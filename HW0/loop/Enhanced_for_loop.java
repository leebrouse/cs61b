public class Enhanced_for_loop {
    public static void main(String[] args) {
        String[] a = {"cat", "dog", "laser horse", "ketchup", "horse", "horbse"};

        for (String s : a) {
            for (int j = 0; j < 3; j += 1) {
                System.out.println(s);
                if (s.contains("horse")) {
                    break;
                }                
            }
        }
    }
}

// Tips：
// for (String s : a)其实与python中的for....in....语句很类似，如果有学过能更好理解！
// 主要目的都是 ‘以简便方法’ 去遍历数组中的数据


// chatgpt:
// for (String s : a) 是 Java 中的增强型for循环（Enhanced for loop）语法，也被称为for-each循环。这种循环专门用于遍历数组或集合中的元素，更简洁和易读。
// 让我们解释一下这段代码：

// java
// Copy code:
// for (String s : a) {
//     // 循环体
// }

// String s：这部分定义了一个变量 s，它是循环中每次迭代时用于存储当前数组元素的变量。在这里，s 是数组 a 中的元素的类型，即字符串类型。
// : a：冒号后面是要被遍历的集合或数组，这里是数组 a。
// 循环体：花括号内的代码块是循环的主体，即在每次迭代时执行的代码。在这个例子中，循环体包含了一个嵌套的for循环，用于处理当前数组元素。
// 这样的循环结构使得遍历数组或集合的代码更加简洁和可读。在每次迭代中，变量 s 就代表了数组 a 中的一个元素，从第一个元素到最后一个元素，循环会自动遍历整个数组。







