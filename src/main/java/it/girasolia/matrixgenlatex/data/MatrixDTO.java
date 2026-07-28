package it.girasolia.matrixgenlatex.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatrixDTO {
    private int rows;
    private int cols;
    private String rowString;
    private String colString;

    private String[][] values;
}
