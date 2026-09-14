package ir.mohaymen.querygenerator.domain.order;

public sealed interface OrderBy permits OrderByColumn, OrderByRaw {
}
