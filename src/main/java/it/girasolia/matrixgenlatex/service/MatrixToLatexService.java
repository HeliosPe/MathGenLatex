package it.girasolia.matrixgenlatex.service;

import it.girasolia.matrixgenlatex.data.MatrixDTO;
import org.springframework.stereotype.Service;

@Service
public class MatrixToLatexService {


    public String process(MatrixDTO matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("$$ \\begin{pmatrix}");
        for(String[] row:matrix.getValues()){


            for (int i = 0; i < row.length; i++) {
                if(row[i].isEmpty()) row[i] = "0";
                if(row[i].contains("...")) row[i] = "\\hdots";
                if(row[i].contains("⋮")) row[i] = "\\vdots";
                if(row[i].contains("⋱")) row[i] = "\\ddots";
                sb.append(row[i]);
                if(i < row.length - 1) sb.append("&");
            }

            sb.append("\\\\");
        }
        sb.append("\\end{pmatrix} $$");

        return sb.toString();
    }

}
