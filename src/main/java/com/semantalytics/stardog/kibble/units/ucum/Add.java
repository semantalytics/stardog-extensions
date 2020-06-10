package com.semantalytics.stardog.kibble.units.ucum;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.units.UcumVocabulary;
import com.stardog.stark.Value;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.fhir.ucum.UcumService;
import tec.units.ri.format.QuantityFormat;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import java.io.IOException;

import static tec.units.ri.unit.Units.KILOGRAM;

public class Add extends AbstractFunction implements UserDefinedFunction {

    public Add() {
        super(1, UcumVocabulary.add.stringValue());
    }

    public Add(final Add add) {
        super(add);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        try {

        } catch (UcumException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Add copy() {
        return new Add(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UcumVocabulary.add.name();
    }

}
