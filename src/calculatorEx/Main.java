package calculatorEx;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
//예외처리()
//1. 계산 식에 맞지 않는 문자 있으면 다시 입력
//2. 괄호 짝 맞지 않으면 다시 입력
//3. 0으로 나누려고 할 때 다시 입력
//4. 띄어쓰기 있으면 다시 입력하도록
//5. 음수 입력 못하도록(맨앞과 괄호 다음만 확인)
//6. int 형 범위애서만 입력 가능하도록 예외처리
//7. 정확한 계산식이 아닐 경우 다시 입력 ex) 1+
//7. 연산자 연속 입력 예외처리 ex) 1++2
//  - 'r' 연산자는 뒤에 연산자가 올 수 있어서 한번 더 예외처리

//추가기능
//1. 콘솔에 계산식 문자열로 입력
//2. 괄호 입력, 제곱(^), 제곱근(r)
//3. 아무곳에서나 exit 입력하여 종료(break loopOut:)
//기록 조회, 맨 처음 데이터 삭제, 모두 삭제 기능 추가

//연산자 우선순위 정하는 함수
public class Main {
    //우선순위 정하는 함수
    static int priority(String checkStr) {
        if (checkStr.equals("(") || checkStr.equals(")")) {
            return 0;
        } else if (checkStr.equals("+") || checkStr.equals("-")) {
            return 1;
        } else if (checkStr.equals("*") || checkStr.equals("/")) {
            return 2;
        } else if (checkStr.equals("^")) {
            return 3;
        } else if (checkStr.equals("r")) {
            return 5;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator cal = new Calculator();

        String str1; //계산할 식 입력 받을 문자열
        int parentheses_num = 0; //괄호 갯수

        Stack<String> Operator_stack = new Stack<>(); //후위 연산에 필요한 연산자, 괄호를 담을 스택

        loopOut:
        while (true) {
            System.out.print("띄어쓰기 없이 계산식을 입력하세요(+, -, x, /, ^, r) : ");
            str1 = sc.nextLine();
            if (str1.contains("exit")) {
                System.out.print("프로그램을 종료합니다");
                break;
            }

            //괄호 짝 맞는지 확인
            String parentheses_str1 = str1;//문자열 임시 저장
            String parentheses_str2 = str1;//문자열 임시 저장
            int parentheses_left = parentheses_str1.length() - parentheses_str1.replace("(", "").length();
            int parentheses_right = parentheses_str2.length() - parentheses_str2.replace(")", "").length();
            if (!(parentheses_left == parentheses_right)) {
                System.out.println("괄호 수가 맞지 않습니다");
                continue;
            }

            //띄어쓰기 있는지 확인
            for (int i = 0; i < str1.length(); i++) {
                if (str1.contains(" ")) {
                    System.out.println("띄어쓰기 없이 다시 입력하세요");
                    continue loopOut;
                }
            }

            //띄어쓰기 기준으로 문자열 나누기 위해 띄어쓰기 만들기
            //한글자씩 나누면 2자리 이상 숫자도 나뉨(25 -> 2, 5)
            str1 = str1.replace("(", " ( ");
            str1 = str1.replace(")", " ) ");
            str1 = str1.replace("+", " + ");
            str1 = str1.replace("-", " - ");
            str1 = str1.replace("/", " / ");
            str1 = str1.replace("*", " * ");
            str1 = str1.replace("^", " ^ ");
            str1 = str1.replace("r", " r ");
            str1 = str1.replace("  ", " "); //띄어쓰기 두개 생겨서 '+('

            String[] inputStr = str1.split(" ");//입력 받은 계산 식 띄어쓰기 기준으로 한글자씩 배열에 저장
            List<String> inputStr_List = new ArrayList<>(); //계산식을 공백 기준으로 담을 리스트

            //문자열 배열 값 리스트에 저장
            for(int i=0; i<inputStr.length; i++) {
                inputStr_List.add(inputStr[i]);
            }

            if(inputStr_List.size() < 3 && !(str1.contains(" r "))) {//루트 연산자가 아니면 3개 이상 있어야 완전한 식
                System.out.println("식이 완전하지 않습니다");
                continue;
            }

            //계산식이 괄호로 시작하면 맨 앞 값이 ""
            if (inputStr_List.get(0).isEmpty()) {
                inputStr_List.remove(0);
            }

            //맨 앞자리가 '-'일떄 다시 입력
            if (inputStr_List.get(0).equals("-")) {
                System.out.println("0보다 큰 수만 입력하세요");
                continue;
            }

            String[] Collection = new String[inputStr_List.size()];//후위 계산식 넣을 배열

            int index = 0; //배열 저장 인덱스
            //배열에 후위식 정렬
            for (int i = 0; i < inputStr_List.size(); i++) {
                String checkStr = inputStr_List.get(i);

                if (checkStr.equals("(")) {
                    //괄호 다음 '-'일때
                    if (inputStr_List.get(i + 1).equals("-")) {
                        System.out.println("0보다 큰 수만 입력하세요");
                        continue loopOut;
                    }
                    Operator_stack.push(checkStr); //시작 괄호면 다 넣기
                    parentheses_num++;
                }
                else if (checkStr.equals(")")) {
                    while (!Operator_stack.isEmpty() && !Operator_stack.peek().equals("(")) {
                        Collection[index] = Operator_stack.pop(); //끝 괄호면 시작 괄호 만날때 까지 다 빼기
                        index++;
                    }
                    Operator_stack.pop();
                    parentheses_num++;
                }
                else if ((checkStr.equals("+")) || checkStr.equals("-") || checkStr.equals("*") || checkStr.equals("/") || checkStr.equals("^") || checkStr.equals("r")) {
                    //1. 연산자 연속으로 오는지 확인 2. 식이 연산자로 끝나는지 확인
                    if (!checkStr.equals("r")) { //식이 r로 끝날 때 [i + 1]조회 시 에러
                        try {
                            if ((inputStr_List.get(i + 1).equals("+")) || inputStr_List.get(i + 1).equals("-") || inputStr_List.get(i + 1).equals("*")
                                    || inputStr_List.get(i + 1).equals("/") || inputStr_List.get(i + 1).equals("^")) {

                                System.out.println("연산자가 연속으로 입력되었습니다");
                                continue loopOut;
                            }
                        }

                        catch (IndexOutOfBoundsException e) {
                            System.out.println("완전한 식이 아닙니다");
                            continue loopOut;
                        }
                    }

                    if (checkStr.equals("/")) {
                        //나누기 다음 값이 0일때
                        if (inputStr_List.get(i + 1).equals("0")) {
                            System.out.println("0으로 나눌 수 없습니다");
                            continue loopOut;
                        }
                    }

                    while (!Operator_stack.isEmpty() && priority(Operator_stack.peek()) >= priority(checkStr)) { //우선순위 함수에서 정한 우선순위대로 스택 넣거나 뺴기
                        Collection[index] = Operator_stack.pop();
                        index++;
                    }
                    Operator_stack.push(checkStr);
                }
                else {
                    //숫자 저장할 때 문자가 있으면 다시 입력
                    for (int k = 0; k < checkStr.length(); k++) {
                        char c = checkStr.charAt(k);
                        if (c < '0' || c > '9') {
                            if (!(c == '.')) {
                                System.out.println("문자는 사용할 수 없습니다");
                                continue loopOut;
                            }
                        }
                    }

                    try {
                        Integer.parseInt(checkStr); //임의로 정수로 변환
                    }
                    catch (NumberFormatException e) { //계산할 숫자 int 변수 범위를 벗어날 때 예외처리
                        System.out.println("-2,147,483,648 ~ 2,147,483,647의 값을 입력하세요");
                        continue loopOut;
                    }

                    Collection[index] = checkStr;//숫자는 게산식 배열에 그냥 넣기
                    index++;
                }
            }

            //스택에 남은 연산자 빼기
            while (!Operator_stack.isEmpty()) {
                Collection[index] = Operator_stack.pop();
                index++;
            }

            String[] new_Collection = Arrays.copyOf(Collection, Collection.length - parentheses_num); //괄호만큼 null 값이 생겨서 배열 새로 생성(parentheses_num : 괄호 숫자)

            cal.setValue(new_Collection);
            cal.getCalculate();

            //연산 이후 동작 선택
            System.out.print("원하는 가능 입력 - 기록 조회 : 1 / 데이터 삭제 : 2 / 모두 삭제 : 3 / 종료 : exit : ");
            String function = sc.nextLine();
            if (function.equals("1")) {
                cal.getResultHistory();
            } else if (function.equals("2")) {
                cal.getRemoveResult(2);
            } else if (function.equals("3")) {
                cal.getRemoveResult(3);
            } else if (function.equals("exit")) {
                break;
            }
        }
    }
}

