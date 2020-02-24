package eu.solutions.a2.cdc.oracle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.solutions.a2.cdc.oracle.utils.ExceptionUtils;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;

/**
 * 
 * @author averemee
 *
 */
public class OraCdcTransaction {

	private static final Logger LOGGER = LoggerFactory.getLogger(OraCdcTransaction.class);

	private final String xid;
	private final long firstChange;
	// Do we need this for pure transaction approach?
	private final String firstRsId;
	// Do we need this for pure transaction approach?
	private final long firstSsn;
	private long commitScn;
	private final Path queueDirectory;
	private ChronicleQueue statements;
	private ExcerptAppender appender;
	private ExcerptTailer tailer;
	private int queueSize = 0;

	public OraCdcTransaction(
			final Path rootDir, final String xid, final OraCdcLogMinerStatement firstStatement) throws IOException {
		this.xid = xid;
		firstChange = firstStatement.getScn();
		firstRsId = firstStatement.getRsId();
		firstSsn = firstStatement.getScn();

		queueDirectory = Files.createTempDirectory(rootDir, xid + ".");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created queue directory {} for transaction XID {}.",
					queueDirectory.toString(), xid);
		}
		try {
			statements = ChronicleQueue
				.singleBuilder(queueDirectory)
				.build();
			appender = this.statements.acquireAppender();
			appender.writeDocument(firstStatement);
			queueSize = 1;
			tailer = this.statements.createTailer();
		} catch (Exception e) {
			LOGGER.error("Unable to create Chronicle Queue!");
			LOGGER.error(ExceptionUtils.getExceptionStackTrace(e));
			throw new IOException(e);
		}
	}

	public void addStatement(final OraCdcLogMinerStatement oraSql) {
		appender.writeDocument(oraSql);
		queueSize++;
	}

	public boolean getStatement(OraCdcLogMinerStatement oraSql) {
		return tailer.readDocument(oraSql);
	}

	public void close() {
		LOGGER.trace("Closing Cronicle Queue and deleting files.");
		statements.close();
		try {
			Files.walk(queueDirectory)
				.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(File::delete);
		} catch (IOException ioe) {
			LOGGER.error("Unable to delete Cronicle Queue files.");
			LOGGER.error(ExceptionUtils.getExceptionStackTrace(ioe));
		}
	}

	public int length() {
		return queueSize;
	}

	public String getXid() {
		return xid;
	}

	public long getFirstChange() {
		return firstChange;
	}

	public String getFirstRsId() {
		return firstRsId;
	}

	public long getFirstSsn() {
		return firstSsn;
	}

	public long getCommitScn() {
		return commitScn;
	}

	public void setCommitScn(long commitScn) {
		this.commitScn = commitScn;
	}

}