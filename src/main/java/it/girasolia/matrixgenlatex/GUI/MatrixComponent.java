package it.girasolia.matrixgenlatex.GUI;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

public class MatrixComponent extends Composite<Div> {

    @Getter
    private int rows;
    @Getter
    private int cols;

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
    }

    public void setGeneralized(boolean generalized) {

        if (!generalized) {

            for (TextField[] row : cells)
                for (TextField tf : row)
                    tf.clear();

            return;
        }

        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {

                cells[i][j].setValue(buildName(i,j));
            }
        }
    }

    private String buildName(int i,int j){

        if(rows==1 && cols==1)
            return "a";

        return "a" + (i+1) + (j+1);
    }
}
