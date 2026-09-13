package ir.mohaymen.querygenerator.domain.having;

import java.util.List;

public record MultipleHaving(List<Having> leftOperand, List<Having> rightOperand) implements Having {
}
