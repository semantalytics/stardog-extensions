package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.semantalytics.stardog.function.multimediafragments.utils.MediaFragmentURIs;
import com.semantalytics.stardog.function.multimediafragments.utils.MediaFragments;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class ToPercent extends AbstractFunction implements UserDefinedFunction {

    public ToPercent() {
        super(3, Constants.NAMESPACE + "toPercent");
    }

    public ToPercent(final ToPercent toPercent) {
        super(toPercent);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(values.length == 3) {
            if(assertIRI(values[0]) && assertLiteral(values[1]) && assertLiteral(values[2])) {
                try {
                    final MediaFragmentURI uri = MediaFragmentURIs.of((IRI)values[0]);
                    final double width = Literal.doubleValue((Literal)values[1]);
                    final double height = Literal.doubleValue((Literal)values[2]);

                    return ValueOrError.General.of(literal(uri.toPercent(width,height).stringValue()));
                } catch (MediaFragmentURISyntaxException e) {
                    return ValueOrError.Error;
                }
            }

            if(assertLiteral(values[0]) && assertLiteral(values[1]) && assertLiteral(values[2])) {
                try {
                    final com.github.tkurz.media.fragments.base.MediaFragment entity = MediaFragments.of((Literal)values[0]);
                    final double width = Literal.doubleValue((Literal)values[1]);
                    final double height = Literal.doubleValue((Literal)values[2]);

                    return ValueOrError.General.of(literal(entity.toPercent(width,height).stringValue()));

                } catch (MediaFragmentURISyntaxException e) {
                    return ValueOrError.Error;
                }
            }
        }

        return ValueOrError.Error;
    }

    @Override
    public ToPercent copy() {
        return new ToPercent(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}