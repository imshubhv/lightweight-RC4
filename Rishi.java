//Para compilar, utilize o comando: javac Rc4.java
//Para executar, utilize o comando: java Rc4 <arquivoEntrada> <arquivoSaída> <chave>
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;

public class Rishi 
{
	//Classe State é a classe que implementa o objeto gerador de sequências pseudo-aleatórias utilizadas pelo algoritmo RC4
    private static class State
    {
        private int[] state = new int[256];
        
        public State (String key)
        {
            int i;
            int j = 0;
            int swap;
            int len = key.length();
			
            for (i = 0 ; i < 256 ; i++)
            {
                this.state[i] = i;
            }
			
            for (i = 0 ; i < 256 ; i++)
            {
                j = (j + this.state[i] + key.charAt((i % len))) % 256;
                swap = this.state[i]; this.state[i] = this.state[j]; this.state[j] = swap;
            }
        }
        
        public ArrayList<Integer> generatePseudoRandom (int length)
        {
            ArrayList<Integer> randomList = new ArrayList<Integer>();
            int i, j, aux, swap;
            i = j = 0;
			
            for (aux = 0 ; aux < length ; aux++)
            {
                i = (i + 1) % 256;
                j = (j + this.state[i]) % 256;
                swap = this.state[i]; this.state[i] = this.state[j]; this.state[j] = swap;
                randomList.add(this.state[(this.state[i] + this.state[j]) % 256]);
            }
            return randomList;
        }
    }

    public static void main(String[] args) 
    {
        FileInputStream in = null;
        FileOutputStream out = null;
        
        State state = new State("Key");
        State state_2 = new State("Key");
        try 
        {    
			//Encryption Part
            byte[] buffer = "abc1234".getBytes();
            byte[] aux = new byte[buffer.length];
			ArrayList<Integer> psRdm = state.generatePseudoRandom(buffer.length);

                for (int i = 0 ; i < buffer.length ; i++)
                {
                    aux[i] = (byte)(buffer[i] ^ psRdm.get(i));
                }
				System.out.println("Encrypted: " + new String(aux));
				
				//Decryption Part
				byte[] aux_2 = new byte[aux.length];
            
                ArrayList<Integer> psRdm_2 = state_2.generatePseudoRandom(aux.length);

                for (int i = 0 ; i < aux.length ; i++)
                {
                    aux_2[i] = (byte)(aux[i] ^ psRdm_2.get(i));
                }
				System.out.println("Decrypted: " + new String(aux_2));
			
		} 
        catch (Exception ex) 
        {
            System.out.println("There was a I/O failure.\n");
        }
    }
}
