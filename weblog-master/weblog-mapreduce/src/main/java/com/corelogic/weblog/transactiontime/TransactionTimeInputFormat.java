package com.corelogic.weblog.transactiontime;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * Reads records that are delimited by a specifc begin/end tag.
 */
public class TransactionTimeInputFormat extends TextInputFormat {

    @Override
    public RecordReader<LongWritable, Text> 
      createRecordReader(InputSplit split,
                         TaskAttemptContext context) {
      return new TransactionTimeRecordReader();
    }
    
}