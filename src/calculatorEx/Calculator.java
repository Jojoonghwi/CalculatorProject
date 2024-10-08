package calculatorEx;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Calculator {
    private String[] new_Collection;

    private Queue<Double> resultQueue = new LinkedList<Double>(); //계산 기록 출력할 Queue
    private Stack<Double> result_Stack = new Stack<Double>();//연산할때 사용할 Stack

    public void setValue(String[] new_Collection) {
        this.new_Collection = new_Collection;
    }

    public void getCalculate() {
        double firstNum;
        double secondNum;
        double result = 0;
        for (int i = 0; i < new_Collection.length; i++) {
            //연산 기호면
            if (new_Collection[i].equals("+")) {
               firstNum = result_Stack.pop();
               secondNum = result_Stack.pop();

                result = secondNum + firstNum;
                result_Stack.add(result);
            }
            else if (new_Collection[i].equals("-")) {
                 firstNum = result_Stack.pop();
                 secondNum = result_Stack.pop();

                result = secondNum - firstNum;
                result_Stack.add(result);
            }
            else if (new_Collection[i].equals("*")) {
                 firstNum = result_Stack.pop();
                 secondNum = result_Stack.pop();

                result = secondNum * firstNum;
                result_Stack.add(result);
            }
            else if (new_Collection[i].equals("/")) {
                 firstNum = result_Stack.pop();
                 secondNum = result_Stack.pop();

                result = secondNum / firstNum;
                result_Stack.add(result);
            }
            //제곱 연산
            else if (new_Collection[i].equals("^")) {
                 firstNum = result_Stack.pop();
                 secondNum = result_Stack.pop();

                result = 1;
                while (firstNum > 0) {
                    result *= secondNum;
                    firstNum--;
                }
                result_Stack.add(result);
            }

            //루트
            else if (new_Collection[i].equals("r")) {
                firstNum = result_Stack.pop();

                result = Math.sqrt(firstNum);
                result_Stack.add(result);
            }

            //숫자면
            else {
                result_Stack.push(Double.parseDouble(new_Collection[i]));
            }
        }
        System.out.println("결과 : " + result);
        resultQueue.add(result);//Queue에 연산 결과 저장
        result_Stack.clear();
    }

    //기록 삭제
    public void getRemoveResult(int a) {
        if (a == 2) {
            resultQueue.poll();
            System.out.println("연산 기록 " + resultQueue);
        }
        else if (a == 3) {
            resultQueue.clear();
            System.out.println("연산 기록 " + resultQueue);
        }
    }

    //결과 조회
    public void getResultHistory() {
        System.out.println("연산 기록 " + resultQueue);
    }
}