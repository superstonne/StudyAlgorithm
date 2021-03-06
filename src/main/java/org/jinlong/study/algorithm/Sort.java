package org.jinlong.study.algorithm;

/**
 * Created by nick on 07/02/2017.
 */
public class Sort {
    private static void swapArrayByIndex(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
    /**
     * 选择排序算法实现，从数组的第一位开始，每次选取一个数字，然后遍历数组，拿这个数字和数组中的每一个数字做比较，获取最小的数字，互换
     * 位置。则数组的长度N，算法复杂度为N平方。
     * @param arr 测试数组
     * @return 返回拍好顺序的结果。
     */
    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int flagIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[flagIndex] > arr[j]) {
                    flagIndex = j;
                }
            }
            int temp = arr[flagIndex];
            arr[flagIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    /**
     * 插入排序算法实现，从数组的第一位开始，按顺序每次取一个数字，然后与数组中当前索引之前的数字做比较，如果这个数字相比之下比较小，则互
     * 换位置，继续与再前一位数字比较直到当前数字不小于前面的数字。算法复杂度为N平方，若原数组近乎有序，则算法复杂度可以优化为N。
     * @param arr
     * @return
     */
    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    break;
                }
            }
        }
        return arr;
    }

    /**
     * 冒泡排序算法实现，假如数组的长度为N,每次都从数组的第一位开始，然后和后一位比较，如果当前位置的数字大于后一位的数字，
     * 则互换位置，如此直到数组的最后一位,遍历直到此次循环中没有可以交换的数字。算法复杂度为N平方。
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr) {
        boolean swap = false;
        do {
            swap = false;
            //此处可以优化为：i+1 < 数组长度-while已经循环的次数
            for (int i = 0; i + 1 < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swap = true;
                }
            }
        } while (swap);
        return arr;
    }

    /**
     * 希尔排序算法实现, 开始将数组分为N/3 份，然后每份进行插入排序；接着将数组分为N／9份，每份进行插入排序；如此直到份数为1，完成最后
     * 一次全数组的插入排序。注意每份中的数组元素不是连续索引的元素，而是间隔获取，例如数组有十个元素，分为3份，则第一份为a[0], a[3],
     * a[6]; 第二份为a[1], a[4], a[7]; 第三份为a[2], a[5], a[8]; 最后一个数字为一组。该算法为不稳定算法，算法复杂度为N的3／2次方。
     * @param arr
     * @return
     */
    public static int[] shellSort(int[] arr) {
        int h = 1;
        //h should be 1, 4, 13...
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < arr.length; i++) {
                int temp = arr[i];
                int j ;
                for (j = i; j >= h && temp < arr[j - h]; j -= h) {
                    arr[j] = arr[j - h];
                }
                arr[j] = temp;
            }
            h = h / 3;
        }
        return arr;
    }

    /**
     * 归并排序算法实现，将数组递归二分，然后归并。若数组长度为N，则数组算法复杂度为N*LOG N。
     * 算法优化思路：
     * 1. 若数组的长度小于15，则采用插入排序。
     * 2. 若arr[middle] <= arr[middle + 1] 则不进行归并，该优化对于近乎有序的数组非常有效，但是对于随机数据会造成性能损耗。
     * @param arr
     * @return
     */
    public static int[] mergeSort(int[] arr) {
        _mergeSort(arr, 0, arr.length - 1);
        return arr;
    }
    private static void _mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = (right - left) / 2 + left;
        _mergeSort(arr, left, middle);
        _mergeSort(arr, middle + 1, right);
        _merge(arr, left, middle, right);

    }
    private static void _merge(int[] arr, int left, int middle, int right) {

        int[] temp = new int[right - left + 1];
        for (int i = left; i <= right; i++) {
            temp[i - left] = arr[i];
        }
        for (int i = left, j = middle + 1, k = left; k <= right; k++) {
            if (i > middle) {
                arr[k] = temp[j - left];
                j++;
            } else if (j > right) {
                arr[k] = temp[i - left];
                i++;
            } else if (temp[i - left] > temp[j - left]) {
                arr[k] = temp[j - left];
                j++;
            } else {
                arr[k] = temp[i - left];
                i++;
            }
        }
    }

    /**
     * 自下而上的归并排序方法实现，算法性能强于自上而下,因为少去了俩步merge sort，直接进行merge。
     * @param arr
     * @return
     */
    public static int[] mergeSortBottomUp(int[] arr) {
        int i = 1;
        while (i <= arr.length) {
            for (int j = 0; j + i < arr.length; j += 2 * i) {
                if (arr[j + i - 1] <= arr[Math.min(j + 2 * i - 1, arr.length - 1)]) {
                    continue;
                }
                _merge(arr, j, j + i - 1, Math.min(j + 2 * i - 1, arr.length - 1));
            }
            i = 2 * i;
        }
        return arr;
    }

    /**
     * 快速排序算法实现，从数组中找到一个标兵，遍历数组，将大于标兵的放在数组右侧，小于标兵的放在数组左侧，如此循环
     * @param arr
     * @return
     */
    public static int[] quickSort(int[] arr) {

        _quickSort(arr, 0, arr.length - 1);
        return arr;
    }
    private static void _quickSort(int[] arr, int left, int right) {

        if (left >= right) {
            return;
        }
        int flagIndex = _partition(arr, left, right);
        _quickSort(arr, left, flagIndex);
        _quickSort(arr, flagIndex + 1, right);
    }
    private static int _partition(int[] arr, int left, int right) {
        int flag = arr[left];
        int j = left;
        int temp = 0;
        for (int i = left + 1; i <= right; i++) {

            if (arr[i] < flag) {
                j++;
                if (j != i) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        temp = arr[j];
        arr[j] = arr[left];
        arr[left] = temp;
        return j;
    }

    /**
     * 快速排序算法，从头尾开始遍历，小于标兵放左边，大于等于标兵放右边。
     * @param arr
     * @return
     */
    public static int[] quickSortTwoWays(int[] arr) {
        _quickSortTwoWays(arr, 0, arr.length - 1);
        return arr;
    }
    private static void _quickSortTwoWays(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = _partitionTwoWays(arr, left, right);
        _quickSortTwoWays(arr, left, middle - 1);
        _quickSortTwoWays(arr, middle + 1, right);
    }
    private static int _partitionTwoWays(int[] arr, int left, int right) {

        int flagIndex = left;
        int flag = arr[left];
        for (int i = left + 1, j = right; i <= j; ) {
            if (arr[i] < flag) {
                i++;
            } else if (arr[j] >= flag) {
                j--;
            } else {
                swapArrayByIndex(arr, i, j);
                i++;
                j--;
            }
            flagIndex = j;
        }
        swapArrayByIndex(arr, left, flagIndex);
        return flagIndex;
    }

    /**
     * 快速排序三路并发实现，小于标兵放左边，大于标兵放右边，等于标兵位置不变。
     * @param arr
     * @return
     */
    public static int[] quickSortThreeWays(int[] arr) {
        _quickSortThreeWays(arr, 0, arr.length - 1);
        return arr;
    }
    private static void _quickSortThreeWays(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] middle = _partitionThreeWays(arr, left, right);
        _quickSortThreeWays(arr, left, middle[0]);
        _quickSortThreeWays(arr, middle[1], right);
    }
    private static int[] _partitionThreeWays(int[] arr, int left, int right) {
        int[] middle = new int[2];
        int lt = left;     // arr[l+1...lt] < v
        int gt = right + 1; // arr[gt...r] > v
        int i = left + 1;    // arr[lt+1...i) == v
        while( i < gt ){
            if( arr[i] < arr[left] ){
                if (i != lt + 1) {
                    swapArrayByIndex(arr, i, lt + 1);
                }
                i++;
                lt++;
            }
            else if( arr[i] > arr[left] ){
                swapArrayByIndex(arr, i, gt - 1);
                gt--;
            }
            else{ // arr[i] == v
                i++;
            }
        }

        swapArrayByIndex(arr, left , lt);
        middle[0] = lt - 1;
        middle[1] = gt;
        return middle;
    }

    /**
     * 堆排序算法实现，使用最大二叉堆实现堆排序，二叉堆定义看这里：http://baike.baidu.com/item/%E4%BA%8C%E5%8F%89%E5%A0%86?fr=aladdin
     * 算法复杂度为N * Log N
     * @param arr
     * @return
     */
    public static int[] heapSort(int[] arr) {
        MaxHeap heap = new MaxHeap(arr.length);
        for (int i = 0; i < arr.length; i++) {
            heap.insertHeap(arr[i]);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = heap.offer();
        }
        return arr;
    }

    /**
     * 堆排序算法实现，加入heapify优化
     * @param arr
     * @return
     */
    public static int[] heapSort1(int[] arr) {
        MaxHeap heap = new MaxHeap(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = heap.offer();
        }
        return arr;
    }
}
