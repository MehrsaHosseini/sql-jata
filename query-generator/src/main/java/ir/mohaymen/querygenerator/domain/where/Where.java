package ir.mohaymen.querygenerator.domain.where;

public sealed interface Where permits BasicWhere, MultipleWhere, WhereExists, WhereNull, WhereRaw, WhereTrue {
}
