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

            in = new FileInputStream(args[0]);
                    out = new FileOutputStream(args[1]);
                    int size;

                    byte[] buffer = new byte[1024];
                    byte[] aux = new byte[1024];
                    while ((size = in.read(buffer)) != -1)
                    {
                      ArrayList<Integer> psRdm = state.generatePseudoRandom(buffer.length);

                  for (int i = 0 ; i < buffer.length ; i++)
                  {
                      aux[i] = (byte)(buffer[i] ^ psRdm.get(i));
                  }
                  out.write(aux);
             }
             in.close();
             out.close();
  				System.out.println("Encrypted: " + new String(aux));
	       }
          catch (Exception ex)
          {
              System.out.println("There was a I/O failure.\n");
          }

    }
}
