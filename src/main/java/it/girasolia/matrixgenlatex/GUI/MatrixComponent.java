package it.girasolia.matrixgenlatex.GUI;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;

public class MatrixComponent extends Composite<Div>{

    @Getter
    private int rows;
    @Getter
    private int cols;
    @Getter @Setter
    private String genTerm = "x";
    @Getter @Setter
    private String rowString = "r";
    @Getter @Setter
    private String colString = "c";

    private boolean generalized = false;
    @Getter
    private TextField[][] cells;

    public MatrixComponent(int rows, int cols) {
        resize(rows, cols);
    }

    public void resize(int newRows, int newCols) {
        this.rows = newRows;
        this.cols = newCols;

        Div grid = getContent();
        grid.removeAll();

        grid.getStyle()
                .set("display", "grid")
                .set("gap", "5px")
                .set("grid-template-columns",
                        "repeat(" + cols + ",100px)");

        cells = new TextField[rows][cols];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {

                TextField tf = new TextField();
                tf.setWidth("90px");

                cells[i][j]=tf;
                grid.add(tf);
            }
        }
        setGeneralized(this.generalized);
    }

    public void setGeneralized(boolean generalized) {
        this.generalized = generalized;
        if (!generalized) {

            for (TextField[] row : cells)
                for (TextField tf : row)
                    tf.clear();

            return;
        }
        int dotsRowsOffset = rows > 5 ? 3 : 2;
        int dotsColsOffset = cols > 5 ? 3 : 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == rows - dotsRowsOffset && j == cols - dotsColsOffset) cells[i][j].setValue("⋱");
                else if (i == rows - dotsRowsOffset) cells[i][j].setValue("⋮");
                else if (j == cols - dotsColsOffset) cells[i][j].setValue("...");
                else cells[i][j].setValue(buildName(i, j));
            }
        }
    }

    private String buildName(int i,int j){
        String appendix1 = i < rows - 2 ? Integer.toString(i + 1)
                : (i < rows - 1 ? rowString + "-1": rowString);
        String appendix2 = j < cols - 2 ? Integer.toString(j + 1)
                : (j < cols - 1 ? colString + "-1": colString);
        return genTerm + "_{" + appendix1 + appendix2 + "}";
    }
}
