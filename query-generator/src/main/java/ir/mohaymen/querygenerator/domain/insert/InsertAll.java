package ir.mohaymen.querygenerator.domain.insert;

import java.util.List;

public record InsertAll(List<InsertSingleRow> insertList) implements Insert {
}
