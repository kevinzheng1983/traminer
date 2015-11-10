# Traminer

Traminer is a Java library for preprocessing, managing and mining spatial trajectory data. Its aim is to help researchers, developers, practitioners and other non-expert professionals to manipulate various vehicle and pedestrian trajectories more easily without re-implementing most fundamental methods. 

The architecture of Traminer consists of three layers and a data reader library, which collectively form a full stack of APIs for trajectory data analysis. 

1.	**Data reader library**:
  More often than not, raw trajectory data collected from business are stored and delivered in a specific data format. In order to make the upstream layers agnostic of the adopted format, this library provides a rich set of data reader classes and methods, which can load trajectory data with various formats and transform them into abstracted trajectory objects. Analogously, a set of readers for digital maps and Point-of-Interest datasets are also provided to load maps and PoI data on disk. 

2. **Data preprocessing layer**:
  Raw trajectories should go through a series of preprocessing steps before they become suitable for indexing, querying and mining. This layer support most basic trajectory preprocessing functions, as is outlined in below. 
  * Noise filtering
  * Trajectory compression
  * Trajectory segmentation (stay point detection)
  * Trajectory calibration
  * Map matching (with map data)
  * Trajectory annotation (with PoI data) 

3.	**Data management layer**:
  This layer deals with a (typically large) collection of trajectory data. It provides implementations of most representative trajectory similarity measures (e.g., LCSS, DTW, ERP, EDR) between two trajectories. It supports a variety of spatio-temporal queries on trajectory data. Indexing and query processing strategies can also be selected according to the storage and computation mode adopted by the business (i.e., disk-based single node, memory-based single node, disk-based cluster, memory-based cluster). 
  * Trajectory similarity measures
  * Spatio-temporal index (platform dependent)
  * Spatio-temporal queries (platform dependent)
  * Semantic-aware trajectory queries (with PoI data)
  * Network-based trajectory queries (with map data)

4.	**Data mining layer**:
  This layer supports a broad range of data mining tasks for trajectory data, which can be directly used by relevant application developers and business analysts. 
  * Frequent pattern discovery
  * Popular route mining
  * Co-traveler discovery
  * Clustering
  * Outlier/event detection
  * Semantic trajectory mining
