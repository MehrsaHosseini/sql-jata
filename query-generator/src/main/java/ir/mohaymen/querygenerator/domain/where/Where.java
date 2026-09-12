package ir.mohaymen.querygenerator.domain.where;

public sealed interface Where permits BasicWhere, WhereExists, WhereNull, WhereRaw, WhereSubQuery, WhereTrue {
}
