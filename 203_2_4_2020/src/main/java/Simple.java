public class Simple
{
    public int add(int number1, int number2)
    {
        return number1 + number2;
    }

    public int subtract(int number1, int number2)
    {
        return number1 - number2;
    }

    public int multiply(int number1, int number2)
    {
        return number1 * number2;
    }

    public int divide(int number1, int number2) throws ArithmeticException
    {
        if(number2 < 1){
            throw new ArithmeticException();
        }
        return number1 / number2;
    }

    public int power(int number1, int number2)
    {
        for(int i = 0; i < number2-1; ++i)
        {
            number1 *= number1;
        }
        return number1;
    }

}
