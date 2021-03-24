// @ author Deep Patel
// @ Thread Synchronization
// this file will look at using
// Java's built in synchronization features to allow threads to coordinate among themselves in a thread
// safe manner. I had used the quicksort approach !


// Java program for the above approach
import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
public class quickSort
    extends RecursiveTask<Integer> {

    int start, end;
    int[] arr;

    private int partion(int start, int end,
                        int[] arr)
    {

        int i = start, j = end;

        int pivote = new Random()
                         .nextInt(j - i)
                     + i;

        int t = arr[j];
        arr[j] = arr[pivote];
        arr[pivote] = t;
        j--;


        while (i <= j) {

            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }

            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }

            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }


        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }


    public quickSort(int start,int end,int[] arr)
    {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute()
    {
        if (start >= end)
            return null;

        int p = partion(start, end, arr);

        quickSort left
            = new quickSort(start,p - 1,arr);

        quickSort right
            = new quickSort(p + 1,end,arr);

        left.fork();
        right.compute();

        left.join();

        return null;
    }


    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        int[] arr = {};
        int n=0;
        int choice = 0;

    	System.out.println("1. For generate random number.");
    	System.out.println("2. For enter manual number.");
    	System.out.println("3. For file input :");
    	System.out.print("Enter your choice : ");

    	try{
    	choice = sc.nextInt();
    	sc.nextLine();

    	}
    	catch(Exception e)
    	{
    		System.out.println("Kindly Enter Number !");
    	}
    	if (choice ==1)
    	{
    		n = (int)(Math.random()*(10000-0+1)+0);
    		arr = new int[n];
    		for(int i=0;i<n;i++)
	        {
	            arr[i] = (int)(Math.random()*(10000-0+1)+0);

	        }

    	}
    	else if (choice ==2)
    	{
    		System.out.print("Enter numbers seperat by comma(,) : ");
        	String str = sc.next();
        	String[] res = str.split("[,]", 0);
        	n = res.length;
        	arr = new int[n];
        	for(int i=0;i<n;i++)
	        {
	            arr[i] = Integer.parseInt(res[i]);

	        }

    	}
    	else if(choice == 3)
    	{
    		try
			{
				System.out.print("Enter file path : ");

	    		String read_file = sc.nextLine();

				File file=new File(read_file);    //creates a new file instance
				FileReader fr=new FileReader(file);   //reads the file
				BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
				StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
				String line;
				while((line=br.readLine())!=null)
				{
				sb.append(line);      //appends line to string buffer
				sb.append(",");     //line feed
				}
				fr.close();    //closes the stream and release the resources

				String str = sb.toString();
	        	String[] res = str.split("[,]", 0);
	        	n = res.length;
	        	arr = new int[n];
	        	for(int i=0;i<n;i++)
		        {
		            arr[i] = Integer.parseInt(res[i]);

		        }
			}
			catch(Exception e)
			{
				// System.out.println(e);
				System.out.println("Kindly Check your file content !");
				System.exit(1);

			}

		}


		System.out.print("Enter number of threads : ");


		int threads = sc.nextInt();

		System.out.println();


		long startTime = System.nanoTime();


        ForkJoinPool pool
            = new ForkJoinPool(threads);

        System.out.printf("Threads before invoke : %d %n",pool.getPoolSize());
        pool.invoke(
            new quickSort(
                0, n - 1, arr));
        System.out.printf("Threads after invoke : %d %n",pool.getPoolSize());

        long endTime = System.nanoTime();


        System.out.println();


        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.println();


        long duration = (endTime - startTime);

        System.out.print((double)duration/1000000000);
        System.out.println(" Seconds");
    }
}
