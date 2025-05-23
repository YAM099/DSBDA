import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class MusicMapper extends Mapper<LongWritable, Text, Text, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() == 0 && value.toString().contains("UserId")) return;
	String[] fields = value.toString().split(",");
        if (fields.length >= 3) {
            String userId = fields[0].trim();
            String trackId = fields[1].trim();
            String shared = fields[2].trim();

            context.write(new Text("U_" + trackId), new Text(userId)); // For unique listeners
            if (shared.equals("1")) {
                context.write(new Text("S_" + trackId), new Text("1")); // For shared count
            }
        }
    }
}

