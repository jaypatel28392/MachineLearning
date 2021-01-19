import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.FileReader;

public class Final_Project
{
    
    static int[] numClasses = {
        5
        , 11
        , 9
        , 9
        , 11
    };;
    static String[][] train_Data;
    static String[][] test_Data;
    static String[][] ongoing_Data;
    static String[] lab_Data;
    static int k, set;
    static Boolean fixed = true;
    
    static String[][] gene_Data;
    
    public static void main(String[] args)
    {
        
        
        String[] training = {
            "TrainData1.txt"
            , "TrainData2.txt"
            , "TrainData3.txt"
            , "TrainData4.txt"
            , "TrainData5.txt"
        };
        int[] sample_Train = {
            150
            , 100
            , 6300
            , 2547
            , 1119
        };
        int[] attributes_Train = {
            3312
            , 9182
            , 13
            , 112
            , 11
        };
        
        String[] testing = {
            "TestData1.txt"
            , "TestData2.txt"
            , "TestData3.txt"
            , "TestData4.txt"
            , "TestData5.txt"
        };
        int[] sample_Test = {
            53
            , 74
            , 2693
            , 1092
            , 480
        };
        int[] attributes_Test = {
            3312
            , 9182
            , 13
            , 112
            , 11
        };
        
        fixed = false;
        for(int i = 0; i < training.length; i++)
        {
            
            getData(training[i], sample_Train[i], attributes_Train[i]);
            ongoing_Data = train_Data;
            fixData();
            writeData(training[i]);
            System.out.println();
        }
        for(int i = 0; i < testing.length; i++)
        {
            
            getData(testing[i], sample_Test[i], attributes_Test[i]);
            ongoing_Data = test_Data;
            fixData();
            writeData(testing[i]);
            System.out.println();
        }
        fixed = true;
        
        
        
        
        String[] latest_Training = {
            "NewTrainData1.txt"
            , "NewTrainData2.txt"
            , "NewTrainData3.txt"
            , "NewTrainData4.txt"
            , "NewTrainData5.txt"
        };
        String[] latest_Testing = {
            "NewTestData1.txt"
            , "NewTestData2.txt"
            , "NewTestData3.txt"
            , "NewTestData4.txt"
            , "NewTestData5.txt"
        };
        String[] labels = {
            "TrainLabel1.txt"
            , "TrainLabel2.txt"
            , "TrainLabel3.txt"
            , "TrainLabel4.txt"
            , "TrainLabel5.txt"
        };
        for(int i = 0; i < latest_Training.length; i++)
        {
            set = (i + 1);
            getData(latest_Training[i], sample_Train[i], attributes_Train[i]);
            getData(latest_Testing[i], sample_Test[i], attributes_Test[i]);
            getData(labels[i], sample_Train[i], 0);
            
            classify();
        }
        
        
        
        
        String[] geneExp = {
            "MissingData1.txt"
            , "MissingData2.txt"
        };
        int[] sample_Gene = {
            14
            , 50
        };
        int[] attributes_Gene = {
            242
            , 758
        };
        
        
        fixed = false;
        for(int i = 0; i < geneExp.length; i++)
        {
            
            getData(geneExp[i], sample_Gene[i], attributes_Gene[i]);
            ongoing_Data = gene_Data;
            fixData();
            writeData(geneExp[i]);
            System.out.println();
        }
        fixed = true;
        
    }
    
    public static void getData(String filename, int samples, int features)
    {
        
        
        System.out.println("Extracting data from" + filename);
        try
        {
            FileReader fileReader = new FileReader("./default/" + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            
            
            if(filename.contains("Label"))
            {
                
                lab_Data = new String[samples];
                
                for(int i = 0; i < lab_Data.length; i++)
                {
                    line = bufferedReader.readLine()
                        .trim();
                    lab_Data[i] = line;
                }
                System.out.println("It is successfully completed.\n");
                
            }
            
            else if(filename.contains("Missing"))
            {
                System.out.println(filename + " Samples: " + samples + " Features: " + features);
                gene_Data = new String[features][samples];
                
                String[] lineSplit;
                for(int i = 0; i < gene_Data.length; i++)
                {
                    line = bufferedReader.readLine()
                        .trim();
                    lineSplit = line.split("\\s+|\\s*,\\s*");
                    for(int j = 0; j < gene_Data[i].length; j++)
                    {
                        gene_Data[i][j] = lineSplit[j];
                    }
                }
                
                
                double d = Math.sqrt(gene_Data.length) / 2;
                k = (int) d;
                bufferedReader.close();
                System.out.println("It is successfully completed.\n");
            }
            
            
            else if(filename.contains("Train"))
            {
                
                System.out.println(filename + " Samples: " + samples + " Features: " + features);
                train_Data = new String[samples][features];
                
                String[] lineSplit;
                for(int i = 0; i < train_Data.length; i++)
                {
                    line = bufferedReader.readLine()
                        .trim();
                    lineSplit = line.split("\\s+|\\s*,\\s*");
                    for(int j = 0; j < train_Data[i].length; j++)
                    {
                        train_Data[i][j] = lineSplit[j];
                    }
                }
                
                
                double d = Math.sqrt(train_Data.length) / 2;
                k = (int) d;
                bufferedReader.close();
                System.out.println("It is successfully completed.\n");
            }
            
            else if(filename.contains("Test"))
            {
                
                
                test_Data = new String[samples][features];
                
                String[] lineSplit;
                for(int i = 0; i < test_Data.length; i++)
                {
                    line = bufferedReader.readLine()
                        .trim();
                    lineSplit = line.split("\\s+|\\s*,\\s*");
                    for(int j = 0; j < test_Data[i].length; j++)
                    {
                        test_Data[i][j] = lineSplit[j];
                    }
                }
                
                double d = Math.sqrt(test_Data.length) / 2;
                k = (int) d;
                bufferedReader.close();
                System.out.println("It is successfully completed.\n");
            }
        }
        catch (IOException io)
        {
            System.out.println("File: " + filename + " not found.");
            System.exit(1);
        }
    }
    
    public static void fixData()
    {
        
        System.out.println("looking for missing values..");
        for(int i = 0; i < ongoing_Data.length; i++)
        {
            int missing = 0;
            System.out.print("Line " + (i + 1) + "... ");
            for(int j = 0; j < ongoing_Data[i].length; j++)
            {
                if(ongoing_Data[i][j].contains("+99"))
                {
                    missing++;
                    String[] query = ongoing_Data[i];
                    
                    
                    Distance[] kNearest = myKnn(i, query);
                    
                    
                    double sum = 0;
                    for(int n = 0; n < k; n++)
                    {
                        Double val = Double.parseDouble(ongoing_Data[kNearest[n].index][j]);
                        sum += val;
                    }
                    
                    String mean = Double.toString(sum / k);
                    
                    ongoing_Data[i][j] = mean;
                }
            }
            System.out.println("missing values corrected: " + missing);
            
        }
        System.out.println("All the missing values were corrected");
    }
    
    public static Distance[] myKnn(int index, String[] query)
    {
        
        Distance[] distances;
        
        if(fixed == false)
        {
            
            
            distances = new Distance[ongoing_Data.length - 1];
            int j = 0;
            
            for(int i = 0; i < ongoing_Data.length; i++)
            {
                String[] instance = ongoing_Data[i];
                Distance d = new Distance(getDistance(instance, query), i);
                if(i != index)
                {
                    distances[j] = d;
                    j++;
                }
            }
        }
        else
        {
            distances = new Distance[train_Data.length];
            for(int i = 0; i < train_Data.length; i++)
            {
                String[] instance = train_Data[i];
                Distance d = new Distance(getDistance(instance, query), i);
                distances[i] = d;
            }
        }
        Arrays.sort(distances);
        Distance[] kNearest = Arrays.copyOfRange(distances, 0, k);
        return kNearest;
    }
    
    public static double getDistance(String[] s1, String[] s2)
    {
        
        
        double distance = 0;
        for(int i = 0; i < s2.length; i++)
        {
            if(s1[i].contains("+99") || s2[i].contains("+99"))
            {
                distance += 0;
            }
            else
            {
                double d1 = Double.parseDouble(s1[i]);
                double d2 = Double.parseDouble(s2[i]);
                double d = Math.pow((d2 - d1), 2);
                distance += d;
                
            }
            
        }
        return Math.sqrt(distance);
    }
    
    public static void writeData(String filename)
    {
        
        String file;
        if(filename.contains("Missing"))
        {
            file = "./success/PatelNew" + filename;
        }
        else
        {
            file = "./default/New" + filename;
        }
        try
        {
            PrintWriter pw = new PrintWriter(file);
            for(int k = 0; k < ongoing_Data.length; k++)
            {
                for(int l = 0; l < ongoing_Data[k].length; l++)
                {
                    pw.print(ongoing_Data[k][l] + "\t");
                }
                pw.print("\n");
            }
            pw.close();
            System.out.println("Fixed data saved to: " + file);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error, file was not found.");
        }
        
        
    }
    
    public static void classify()
    {
        
        String[] results = new String[test_Data.length];
        
        for(int i = 0; i < test_Data.length; i++)
        {
            
            String[] query = test_Data[i];
            Distance[] kNearest = myKnn(-1, query);
            
            String[] labels = new String[kNearest.length];
            for(int j = 0; j < kNearest.length; j++)
            {
                labels[j] = lab_Data[kNearest[j].index];
            }
            int res = getWeighted(labels, kNearest);
            
            results[i] = "" + res;
        }
        
        
        try
        {
            PrintWriter pw = new PrintWriter("./success/PatelClassification" + set + ".txt");
            for(int k = 0; k < results.length; k++)
            {
                System.out.println(results[k]);
                pw.print(results[k] + "\n");
            }
            pw.close();
            System.out.println("Testing labels saved to: PatelClassification" + set + ".txt\n");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
    }
    
    public static int getWeighted(String[] labels, Distance[] kNearest)
    {
        
        int[] classes = new int[numClasses[set - 1]];
        double[] scores = new double[classes.length];
        for(int i = 0; i < classes.length; i++)
        {
            classes[i] = i + 1;
        }
        
        for(int j = 0; j < classes.length; j++)
        {
            for(int k = 0; k < labels.length; k++)
            {
                if(String.valueOf(classes[j])
                    .equals(labels[k]))
                {
                    scores[j] += 1 / kNearest[k].distValue;
                }
            }
        }
        double[] sorted = scores.clone();
        Arrays.sort(sorted);
        for(int l = 0; l < scores.length; l++)
        {
            if(sorted[(sorted.length) - 1] == scores[l])
            {
                return classes[l];
            }
        }
        return -1;
    }
}

class Distance implements Comparable < Distance >
{
    
    
    double distValue;
    int index;
    
    Distance(double distValue, int index)
    {
        this.distValue = distValue;
        this.index = index;
    }
    
    public int compareTo(Distance d)
    {
        if(distValue == d.distValue)
        {
            return 0;
        }
        else if(distValue > d.distValue)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    public String toString()
    {
        return "Index: " + index + " Distance: " + distValue + "\n";
    }
    
}
