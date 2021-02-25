package com.semantalytics.stardog.kibble.bites.ipfs.impl;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.docs.BitesException;
import com.complexible.stardog.docs.DocumentStore;
import com.complexible.stardog.docs.impl.DocumentStoreImpl;
import com.google.api.client.util.Preconditions;
import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IpfsDocumentStoreImpl implements DocumentStore {

    private final IPFS ipfs;

    private static final Logger LOGGER = LoggerFactory.getLogger(IpfsDocumentStoreImpl.class);

    public IpfsDocumentStoreImpl(final MultiAddress apiMultiAddress) {
        Preconditions.checkNotNull(apiMultiAddress);
        this.ipfs = new IPFS(apiMultiAddress);
    }

    @Override
    public long size() {
        List<Multihash> locals;
        try {
            locals = ipfs.refs.local();
        } catch (IOException e) {
            throw new StardogException(e);
        }
        return locals.size();
    }

    @Override
    public Path update(final Path path) throws BitesException {
        try {
            return update(path.toFile().getName(), Files.newInputStream(path));
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public Path update(final String theDocName, final InputStream theInputStream) throws BitesException {
        try {
            Path aDocPath = getDocPath(theDocName);
            Files.createDirectories(aDocPath.getParent());
            if (Files.exists(aDocPath)) {
                Files.delete(aDocPath);
            }
            Files.copy(theInputStream, aDocPath);
            return aDocPath;
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        finally {
            try {
                theInputStream.close();
            }
            catch (IOException e) {
                // don't wrap or re-throw as it will obscure any exception that occurs while writing file
                LOGGER.error("Failed to close input stream while writing file", e);
            }
        }
    }

    @Override
    public void remove(String s) throws BitesException {

    }

    @Override
    public Path get(String s) throws BitesException {
        return null;
    }

    @Override
    public boolean contains(String s) throws BitesException {
        return false;
    }

    @Override
    public void clear() throws BitesException {

    }

    @Override
    public void close() throws IOException {

    }
    /**
     * Get the file path corresponding to the document with the given name
     */
    private Path getDocPath(String theDocName) {
        String aDocId = docNameToId(theDocName);
        String aPrefixDir = aDocId.substring(0, 2);
        return mRoot.resolve(aPrefixDir).resolve(aDocId);
    }
}
