package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import java.util.stream.Stream;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.*;

public class Console extends AbstractFunction implements UserDefinedFunction {

        public Console() {
            super(Range.all(), ConsoleVocabulary.console.stringValue());
        }

        public Console(final Console console) {
            super(console);
        }

        @Override
        protected ValueOrError internalEvaluate(final Value... values) {
            final Ansi ansi = ansi();

            Stream.of(values).map(v -> assertLiteral(v) ? ((Literal)v).label() : v.toString()).forEach(s -> ansi.a(s));

            return ValueOrError.General.of(literal(ansi.reset().toString()));
        }

        @Override
        public Console copy() {
            return new Console(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return ConsoleVocabulary.console.name();
        }
}
