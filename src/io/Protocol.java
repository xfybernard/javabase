package io;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Protocol {
	public void handleAcceptable(SelectionKey key) throws IOException;
	public void handleReadable(SelectionKey key) throws IOException;
	public void handleWriteable(SelectionKey key) throws IOException;
}
