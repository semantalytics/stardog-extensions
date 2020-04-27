package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.complexible.stardog.plan.filter.functions.hash.SHA1;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.openrdf.model.Value;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Save extends AbstractFunction implements UserDefinedFunction {

    private static final String SLASH = File.separator;
    private static String downloadsDir;

    static {
        if(System.getenv("STARDOG_HOME") == null) {
            downloadsDir = System.getProperty("user.home") + SLASH + "stardog-downloads";
        } else {
            downloadsDir = System.getenv("STARDOG_HOME") + SLASH + "downloads";
        }
    }

    private File getDestinationFile(final String hash) {
        return new File(downloadsDir + SLASH + hash.substring(0, 4) + SLASH + hash.substring(4,8) + SLASH + hash.substring(7));
    }

    public Save() {
        super(1, FileVocabulary.save.stringValue());


    }

    private Save(final Save cat) {
        super(cat);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertLiteral(values[0]);

        final String hash;

        final MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch(NoSuchAlgorithmException e) {
            throw new ExpressionEvaluationException(e);
        }

        hash = messageDigest.digest(values[0].stringValue().getBytes()).toString();

        final File destFile = getDestinationFile(hash);

        try {
            Files.createParentDirs(getDestinationFile(hash));
        } catch(IOException e) {
            throw new ExpressionEvaluationException("Unable to create parent directories to final destination");
        }

        try {
            destFile.createNewFile();
        } catch(IOException e) {
            throw new ExpressionEvaluationException("Unable to create destination file");
        }

        if (!destFile.exists()) {
            try {
                Files.write(values[0].stringValue().getBytes(), destFile);
            } catch(IOException e) {
                throw new ExpressionEvaluationException("Unable to copy temp final to destination");
            }
        }

        return Values.iri(destFile.toURI().toString());
    }

    @Override
    public Function copy() {
        return new Save(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.save.name();
    }
}
