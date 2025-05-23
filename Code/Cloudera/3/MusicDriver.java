import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MusicDriver {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: MusicDriver <input path> <output path>");
            System.exit(-1);
        }
	Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Music Stats");
	job.setJarByClass(MusicDriver.class);
        job.setMapperClass(MusicMapper.class);
        job.setReducerClass(MusicReducer.class);
	job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
