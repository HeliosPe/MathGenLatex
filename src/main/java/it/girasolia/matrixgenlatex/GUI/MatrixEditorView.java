package it.girasolia.matrixgenlatex.GUI;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import it.girasolia.matrixgenlatex.data.MatrixDTO;
import it.girasolia.matrixgenlatex.service.MatrixToLatexService;

import java.util.Arrays;


@Route("")
public class MatrixEditorView extends VerticalLayout {
    private final MatrixToLatexService service;

    HorizontalLayout toolbar;

    private int rowsNum = 3;
    private int colsNum = 3;

    private final TextField rowsField = new TextField("Строк");
    private final TextField colsField = new TextField("Колонок");

    private final Button addRow = new Button("+ Строка");
    private final Button removeRow = new Button("- Строка");

    private final Button addCol = new Button("+ Колонка");
    private final Button removeCol = new Button("- Колонка");

    private final Checkbox generalized =
            new Checkbox("Обобщенная матрица");

    private final TextField elementField =
            new TextField("Общий элемент");

    private final Button alpha = new Button("α");
    private final Button beta = new Button("β");
    private final Button lambda = new Button("λ");

    private final MatrixComponent matrix = new MatrixComponent(3,3);
    private final Button generate = new Button("Получить результат");
    private final TextArea resultArea = new TextArea("Результат");
    private final Button copy = new Button("Копировать");

    public MatrixEditorView(MatrixToLatexService service) {
        this.service = service;

        rowsField.setValue("r");
        colsField.setValue("c");

        resultArea.setWidthFull();
        resultArea.setHeight("300px");

        toolbar = new HorizontalLayout(
                addRow,
                removeRow,
                addCol,
                removeCol,
                generalized
        );

        add(toolbar, matrix, generate, resultArea, copy);

        addRow.addClickListener(e -> {
            ++rowsNum;
            resizeSafe(matrix, rowsNum, colsNum);
        });

        removeRow.addClickListener(e -> {
            rowsNum = Math.max(1, rowsNum - 1);
            resizeSafe(matrix, rowsNum, colsNum);
        });

        addCol.addClickListener(e -> {
            ++colsNum;
            resizeSafe(matrix, rowsNum, colsNum);
        });

        removeCol.addClickListener(e -> {
            colsNum = Math.max(1, colsNum - 1);
            resizeSafe(matrix, rowsNum, colsNum);
        });

        generalized.addValueChangeListener(e->{
            if(e.getValue()){
                toolbar.add(rowsField);
                toolbar.add(colsField);
                toolbar.add(elementField);
                matrix.setGeneralized(true);
            } else{
                toolbar.remove(rowsField);
                toolbar.remove(colsField);
                toolbar.remove(elementField);
                matrix.setGeneralized(false);
            }
        });

        rowsField.addValueChangeListener(e->{
            matrix.setRowString(e.getValue());
            matrix.setGeneralized(true);
        });

        colsField.addValueChangeListener(e->{
            matrix.setColString(e.getValue());
            matrix.setGeneralized(true);
        });

        elementField.addValueChangeListener(e ->{
            matrix.setGenTerm(e.getValue());
            matrix.setGeneralized(true);
        });

        elementField.addFocusListener(e-> {
            toolbar.add(alpha);
            toolbar.add(beta);
            toolbar.add(lambda);
        });

        alpha.addClickListener(e-> greekLetterListener("\\alpha"));
        beta.addClickListener(e-> greekLetterListener("\\beta"));
        lambda.addClickListener(e-> greekLetterListener("\\lambda"));

        generate.addClickListener(e -> {
            MatrixDTO dto = collectMatrix();

            String result = service.process(dto);

            resultArea.setValue(result);

        });

        copy.addClickListener(e -> {
            UI.getCurrent()
                    .getPage()
                    .executeJs(
                            "navigator.clipboard.writeText($0)",
                            resultArea.getValue()
                    );
            Notification.show("Скопировано");
        });
    }

    private void greekLetterListener(String letterLatex){
        toolbar.remove(alpha);
        toolbar.remove(beta);
        toolbar.remove(lambda);

        elementField.setValue(letterLatex);
    }

    private MatrixDTO collectMatrix(){

        MatrixDTO dto = new MatrixDTO();

        dto.setRows(rowsNum);
        dto.setCols(colsNum);

        String[][] values = Arrays.stream(matrix.getCells()).map(e->
                Arrays.stream(e).map(TextField::getValue).toArray(String[]::new)
        ).toArray(String[][]::new);

        dto.setRowString(rowsField.getValue());
        dto.setColString(colsField.getValue());
        dto.setValues(values);

        return dto;
    }

    private void resizeSafe(MatrixComponent matrix, int rows, int cols){
        String[][] values = Arrays.stream(matrix.getCells()).map(e->
                Arrays.stream(e).map(TextField::getValue).toArray(String[]::new)
        ).toArray(String[][]::new);

        matrix.resize(rows, cols);

        if(generalized.getValue()){
            matrix.setGeneralized(true);
        } else{
            for (int i = 0; i < Math.min(matrix.getRows(), rows); i++) {
                for(int j = 0; j < Math.min(matrix.getCols(), cols); j++) {
                    matrix.getCells()[i][j].setValue(values[i][j]);
                }
            }
        }
    }
}
