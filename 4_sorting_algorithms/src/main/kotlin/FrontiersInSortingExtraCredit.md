# LearnedSort: An ML-enhanced Approach to Sorting

### Intro

The main goal of sorting algorithms is to organize data as efficiently as possible.
This becomes particularly evident when comparing different sorting algorithms' time
and space complexities, and informs decisions on which algorithms to use given a certain-sized
dataset. For this assignment I chose to research LearnedSort, a new sorting algorithm being
developed that utilizes machine learning to speed up the sorting of data. LearnedSort and its
successor LearnedSort 2.0 both use machine learning to estimate the distribution of data in a
dataset by taking a sample of the keys and using it to predict the ordering of the keys
in the sorted output.


### Paper Summary and Discussion

To learn more about LearnedSort and its more efficient successor LearnedSort 2.0, I read "Defeating Duplicates:
A Re-Design of the LearnedSort Algorithm" by Ani Kristo, Kapil Vaidya, and Tim Kraska. The paper
discusses the methodology behind LearnedSort and how it uses empirical cumulative distribution function (eCDF)
to predict the distribution of data in a dataset. It is then able to sort data using fixed-capacity buckets
to catch the keys and sort them internally, while maintaining a spill bucket that is sorted using traditional
sorting methods such as std::sort (which uses IntroSort) in case the fixed-capacity buckets became full. The paper notes that while LearnedSort was 
up to 29% faster than the next-best sorting algorithm and 49% faster than Radix Sort, it's runtime suffered significantly 
when provided with highly-duplicate datasets. In these cases the fixed-sized buckets would quickly overflow, and 
the spill bucket would be sorted using std::sort, which impacted performance.

The paper presents LearnedSort 2.0 and how it's different from LearnedSort. Instead of using fixed-size buckets, 
LearnedSort 2.0 uses a dynamical approach by emulating buckets of variable size by utilizing a combination of overwriting
the initial dataset and fragmenting buckets. The paper reports that LearnedSort 2.0 runs up to 378% faster for all datasets 
containing high amounts of duplicates, while also running up to 60% faster on low-duplicate datasets. The paper proposes to
extend LearnedSort to parallel processes and handling disk-scale data as potential future work.