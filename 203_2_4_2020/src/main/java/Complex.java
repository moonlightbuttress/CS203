public class Complex {

    Complex(){}

    public double squareRoot(int number) {
        if (number == 0 || number == 1){
            return number;
        }
        if(number < 0){
            throw new ArithmeticException();
        }
        double t;

        double squareRoot = (double)number / 2;

        do {
            t = squareRoot;
            squareRoot = (t + (number / t)) / 2;
        } while ((t - squareRoot) != 0);

        return squareRoot;
    }


    public int factorial(int number)
    {
        if(number == 0){
            return 1;
        }
        if(number < 0){
            throw new ArithmeticException();
        }
        int fact = 1;

        for(int i = number; i > 0; --i)
        {
            fact *= i;
        }

        return fact;
    }

}