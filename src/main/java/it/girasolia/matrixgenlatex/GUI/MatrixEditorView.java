package it.girasolia.matrixgenlatex.GUI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

public class MatrixEditor {

    @Route("matrix")
    public class MatrixEditorView extends VerticalLayout {

        private final IntegerField rowsField = new IntegerField("Строк");
        private final IntegerField colsField = new IntegerField("Колонок");

        private final Button addRow = new Button("+ Строка");
        private final Button removeRow = new Button("- Строка");

        private final Button addCol = new Button("+ Колонка");
        private final Button removeCol = new Button("- Колонка");

        private final Checkbox generalized =
                new Checkbox("Обобщенная матрица");

        private final MatrixComponent matrix = new MatrixComponent(3,3);

        public MatrixEditorView() {

            rowsField.setValue(3);
            colsField.setValue(3);

            HorizontalLayout toolbar = new HorizontalLayout(
                    rowsField,
                    colsField,
                    addRow,
                    removeRow,
                    addCol,
                    removeCol,
                    generalized
            );

            add(toolbar, matrix);

            rowsField.addValueChangeListener(e ->
                    matrix.resize(e.getValue(), matrix.getCols()));

            colsField.addValueChangeListener(e ->
                    matrix.resize(matrix.getRows(), e.getValue()));

            addRow.addClickListener(e ->
                    rowsField.setValue(matrix.getRows()+1));

            removeRow.addClickListener(e ->
                    rowsField.setValue(Math.max(1,matrix.getRows()-1)));

            addCol.addClickListener(e ->
                    colsField.setValue(matrix.getCols()+1));

            removeCol.addClickListener(e ->
                    colsField.setValue(Math.max(1,matrix.getCols()-1)));

            generalized.addValueChangeListener(e ->
                    matrix.setGeneralized(e.getValue()));
        }
    }

}
