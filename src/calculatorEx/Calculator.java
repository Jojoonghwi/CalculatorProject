package calculatorEx;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Calculator {
    private int sqrt_result;//제곱근 결과 실수형
    private String[] new_Collection;

    private Queue<Integer> resultQueue = new LinkedList<Integer>(); //계산 기록 출력할 Queue
    private Stack<Integer> result_Stack = new Stack<Integer>();//연산할때 사용할 Stack

    public void setValue(String[] new_Collection) {
        this.new_Collection = new_Collection;
    }

    public double getCalculate() {
        int firstNum;
        int secondNum;
        int result = 0;
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

                result = (int)Math.sqrt(firstNum);
                result_Stack.add(result);
            }

            //숫자면
            else {
                try {
                    result_Stack.push(Integer.parseInt(new_Collection[i]));
                }
                catch (NumberFormatException e) {   //예외처리4. 계산할 두 식 int 변수 범위를 벗어날 때 예외처리
                    System.out.println("-2,147,483,648 ~ 2,147,483,647의 값을 입력하세요");
                    return 0;
                }
            }
        }
        System.out.println("결과 : " + result);
        resultQueue.add(result);//Queue에 연산 결과 저장
        result_Stack.clear();

        return result;
    }

    //한개 삭제 후 연산 결과 조회
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

    //연산 결과 조회
    public void getResultHistory() {
        System.out.println("연산 기록 " + resultQueue);
    }
}