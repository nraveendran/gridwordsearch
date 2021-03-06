Instruction to Run
-------------------

Build the project using

mvn clean install

The files (englishwords.txt and grid.txt) which came with the problem is checked in with the source code. Running the following will use these files to generate output

mvn exec:java -Dexec.mainClass="com.mine.nidhish.GridRunner"

In order to specify external input files, please pass two system properties as specified below

mvn exec:java -Dexec.mainClass="com.mine.nidhish.GridRunner" -DdictionaryFile=/home/nidhish/Downloads/englishwords.txt -DgridFile=/home/nidhish/Downloads/grid.txt


Approach
---------

1) In order to store the dictionary, I am using a Trie data structure since its seems to be the best fit for this kind of problems
2) The Trie dictionary implementation provides api to check if a sequence of characters is a word in the dictionary
3) The Trie dictionary also provides api which specifies if a character sequence is a prefix of a word.
4) The Grid is scanned cell by cell from the left top to the right bottom and each cell is added to a cell path (a patch is a sequence of characters)
5) Each cell path  is checked against the dictionary to see if that path is a word. If its a word, then the word is added to
   the output set of word.
6) If the cell path is a prefix to a possible word, new paths are formed by scanning all possible neighbors that are not in the current path
7) For each one of the new paths created, steps 5 and 6 are repeated until there are no remaining paths


Recursion vs iteration and scalability
--------------------------------------

Most solutions I saw in the internet for similar problems are based on recursion and it might provide a memory advantage in case of big grids.
But since it was specifically stated in the problem to avoid recursion and since scalability is a criteria I took the "messaging" approach mentioned above.
IMPORTANT: In my mind, the advantage of this approach is that each cell path can be treated as a separate message and can be submitted to a thread pool to compute
if its a word and new cell paths. So even when there is an explosion of cell paths because of large grid say (20 million) we are able to parallel process the generated cell paths.
To take this one step ahead, we can scale this solution across machines by replacing the internal thread pool with an external queue (like JMS) and storing the dictionary in
memcached or Redis.

I started working on a multi-threaded approach but due to time constraints, I don't have a working solution for that yet.

Performance test
----------------

The sample set runs under 1s in my machine

I have included a performance test which generates a 1 million element grid (1000 * 1000). The serial algorithm I have is able to find words in 52 seconds in my machine.
I believe performance should improve with a multi-threaded approach.


The performance test can be run by

mvn exec:java -Dexec.mainClass="com.mine.nidhish.GridPerformanceTest"




