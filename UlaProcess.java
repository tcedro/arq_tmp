import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class UlaProcess {
    // valores das entradas e operacao a ser realizada 
    private static char x;
    private static char y;
    private static char op;

    // arquivos de entrada e saida
    private static final String arquivoUla = "testeula.ula";
    private static final String arquivoHex = "testeula.hex";

    // parse da primeira entrada (x)
    public static void lerX (String instrucao) {
        if (instrucao.length()==4) { // numero de um digito
            x = instrucao.charAt(2);
        } else { // numero de dois digitos
            switch(instrucao.substring(2,4)) {
                case "10": x = 'A'; break;
                case "11": x = 'B'; break;
                case "12": x = 'C'; break;
                case "13": x = 'D'; break;
                case "14": x = 'E'; break;
                case "15": x = 'F'; break;
            }
        }
    }

    // parse da segunda entrada (y)
    public static void lerY (String instrucao) {
        if (instrucao.length()==4) { // numero de um digito 
            y = instrucao.charAt(2);
        } else {
            switch(instrucao.substring(2,4)) { // numero de dois digitos
                case "10": y = 'A'; break;
                case "11": y = 'B'; break;
                case "12": y = 'C'; break;
                case "13": y = 'D'; break;
                case "14": y = 'E'; break;
                case "15": y = 'F'; break;
            }
        }
    }

    // parse da operacao a ser realizada (op)
    public static void lerOP (String instrucao) {
        // separa operacao depois do = ate antes do ;
        String operacao = instrucao.substring(2,instrucao.length()-1);

        switch (operacao) { // verifica qual a operacao
            case "nA":     op = '0'; break;
            case "AoBn":   op = '1'; break;
            case "nAeB":   op = '2'; break;
            case "Lzero":  op = '3'; break;
            case "AeBn":   op = '4'; break;
            case "nB":     op = '5'; break;
            case "AxB":    op = '6'; break;
            case "AenB":   op = '7'; break; 
            case "nAoB":   op = '8'; break;
            case "AxBn":   op = '9'; break;
            case "Bcopia": op = 'A'; break;
            case "AeB":    op = 'B'; break;
            case "Lum":    op = 'C'; break;
            case "AonB":   op = 'D'; break;
            case "AoB":    op = 'E'; break;
            case "Acopia": op = 'F'; break;
        }
    }

    // gera linha de codigo da operacao em hexadecimanl
    public static String gerarCodigo() {
        return "" + x + y + op + "\n";
    }

    // funcao principal
    public static void main (String args[]) throws IOException {
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            // abre arquivos de leitura e escrita
            in = new BufferedReader(new FileReader(arquivoUla));
            out = new BufferedWriter(new FileWriter(arquivoHex));

            in.readLine(); // le "inicio:"

            String linhaLida = in.readLine(); // le primeira linha do pseudocodigo
            
            while (!linhaLida.equals("fim."))  { // comparacao p/terminar pseudocodigo
                boolean mudouOP = false; // indica se a operacao foi alterada ou nao
                char c = linhaLida.charAt(0);

                switch (c) { // verifica se primeiro char eh uma entrada ou operacao
                    case 'X': lerX(linhaLida); break;
                    case 'Y': lerY(linhaLida); break;
                    case 'W': lerOP(linhaLida); mudouOP = true; break;
                }
                
                if(mudouOP) // condicao p/escrever codigo no arquivo .hex
                    out.write(gerarCodigo());   
                
                linhaLida = in.readLine(); // le proxima linha ao final p/repeticao
            }

            // fecha arquivos
            in.close();
            out.close();
        } catch (IOException e) { // tratamento de excecao de leitura/escrita
            System.out.println("\nERRO: ao tentar ler ou escrever em arquivo!\n");
        }
    }

}
