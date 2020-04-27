package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.apache.tika.Tika;
import org.openrdf.model.IRI;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.complexible.common.rdf.model.Values.*;

public class Cat extends AbstractFunction implements UserDefinedFunction {

    private Tika tika;

    Cat() {
        super(Range.all(), FileVocabulary.cat.stringValue());
    }

    private Cat(final Cat cat) {
        super(cat);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        for(final Value value : values) {
            assertFileIriOrStringLiteral(value);
        }

        final StringBuffer sb = new StringBuffer();
        for(final Value value : values) {
            sb.append(stringValue(value));
        }
        return literal(sb.toString());
    }

    @Override
    public void initialize() {
        tika = new Tika();
    }

    @Override
    public Cat copy() {
        return new Cat(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.cat.name();
    }

    private static boolean isFileIri(final Value value) {
        if(isIri(value) && ((IRI)value).stringValue().startsWith("file:")) {
            return true;
        } else {
            return false;
        }
    }

    public static String stringValue(final Value value) throws ExpressionEvaluationException {
        if(isFileIri(value)) {
            final URI uri;
            try {
                uri = new URI(value.stringValue());
            } catch(URISyntaxException e) {
                throw new ExpressionEvaluationException("Unable to parse URI " + value.stringValue());
            }


            final byte[] encoded;

            try {
                encoded = Files.readAllBytes(Paths.get(uri));
            } catch(IOException e) {
                throw new ExpressionEvaluationException("Unable to open file " + uri);
            }

            return new String(encoded);
        } else {
            return value.stringValue();
        }
    }

    private static Value assertFileIriOrStringLiteral(final Value value) throws ExpressionEvaluationException {
        if(isFileIri(value) || isStringLiteral(value)) {
            return  value;
        } else {
            throw new ExpressionEvaluationException("Argument must be a either a string literal or file: IRI");
        }
    }

    private static boolean isStringLiteral(final Value value) {
        if(value instanceof Literal && EvalUtil.isStringLiteral((Literal) value)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isIri(final Value value) {
        return value instanceof IRI ? true : false;
    }
}
