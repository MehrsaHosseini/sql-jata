package ir.mohaymen.querygenerator.domain.where;

import java.util.List;

public record MultipleWhere(List<Where> leftOperand, List<Where> rightOperand) implements Where {
}
