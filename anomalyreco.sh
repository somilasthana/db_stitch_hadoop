#hadoop fs -rmr /story_unique_detail/output
#hadoop jar target/dbhadoop-1.0-SNAPSHOT-jar-with-dependencies.jar com.db.index.HindiWordFreqInStoryDriver
#curl -XDLETE 'http://172.31.31.152:9200/recommendation/_query' -d '{
#
#     "query": {
#        "match_all": {}
#    }

#}'
#export JAVA_HOME="/home/centos/workspace/Akshay/jdk1.8.0_141"
#echo $JAVA_HOM
#export JAVA_HOME="/opt/jdk1.8.0_141"
export PATH=$PATH:$JAVA_HOME/bin
export HADOOP_HOME=/opt/hadoop
export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export HADOOP_CONF_DIR=$HADOOP_HOME
export HADOOP_PREFIX=$HADOOP_HOME
export HADOOP_LIBEXEC_DIR=$HADOOP_HOME/libexec
export JAVA_LIBRARY_PATH=$HADOOP_HOME/lib/native:$JAVA_LIBRARY_PATH
export HADOOP_CONF_DIR=$HADOOP_PREFIX/etc/hadoop

date
/opt/hadoop/bin/hadoop jar target/dbhadoop-1.0-SNAPSHOT-jar-with-dependencies.jar com.dainik.bhaskar.anomalousreco.AnomalousRecommendationMainClass 
date
