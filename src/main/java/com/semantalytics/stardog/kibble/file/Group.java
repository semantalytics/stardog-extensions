package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import java.net.URI;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.GregorianCalendar;

import static com.complexible.common.rdf.model.Values.*;

public class Group extends AbstractFunction implements UserDefinedFunction {

    Group() {
        super(1, FileVocabulary.group.stringValue());
    }

    private Group(final Group contentType) {
        super(contentType);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertIRI(values[0]);
        Path path = Paths.get(URI.create(values[0].stringValue()));
        GroupPrincipal groupPrincipal;
        try {
            groupPrincipal = Files.readAttributes(path, PosixFileAttributes.class).group();
        } catch (IOException e) {
            throw new ExpressionEvaluationException("Unable to read file attributes");
        }
        return literal(groupPrincipal.getName());

    }

    @Override
    public Function copy() {
        return new Group(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.group.name();
    }
}
