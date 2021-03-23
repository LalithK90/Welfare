package lk.avsec_welfare.util.service;


import org.springframework.stereotype.Service;


@Service
public class MakeAutoGenerateNumberService {
    private final DateTimeAgeService dateTimeAgeService;

    public MakeAutoGenerateNumberService(DateTimeAgeService dateTimeAgeService) {
        this.dateTimeAgeService = dateTimeAgeService;
    }

    public Integer numberAutoGen(String lastNumber) {
        int newNumber;
        int previousNumber;
        int newNumberFirstTwoCharacters;

        int currentYearLastTwoNumber =
                Integer.parseInt(String.valueOf(dateTimeAgeService.getCurrentDate().getYear()).substring(2, 4));
//if it has own number
        if (lastNumber != null) {
            previousNumber = Integer.parseInt(lastNumber);
            //first two digits of last record
            newNumberFirstTwoCharacters = Integer.parseInt(lastNumber.substring(0, 2));
//if first two number is equal
            if (currentYearLastTwoNumber == newNumberFirstTwoCharacters) {
                newNumber = previousNumber + 1;
            } else {
                newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000");
            }
        }
        // if it has not own last number
        else {
            newNumber = Integer.parseInt(currentYearLastTwoNumber + "0000");
        }
        System.out.println("new number " + newNumber);
        return newNumber;
    }

    // phone number length validator
    public String phoneNumberLengthValidator(String number) {
        if (number.length() == 9) {
            number = "0".concat(number);
        }
        return number;
    }

    //make new NIC using old one
    public String makeNewNIC(String oldNIC) {
        String newNic = "";
        String firstTwo = oldNIC.substring(0, 2);
        if (oldNIC.length() < 12 && !firstTwo.equals("00")) {
            int firstTwoValue = Integer.parseInt(firstTwo);
            if (firstTwoValue < 99) {
                newNic = "19".concat(oldNIC.substring(0, 5)).concat("0").concat(oldNIC.substring(5, 9));
            }
        } else {
            newNic = oldNIC;
        }
        return newNic;
    }

    //make old nic number Using new number
    public String makeOldNIC(String newNIC) {
        String oldNic = "";
        if (newNIC.length() > 10) {
            String withOutYear = newNIC.substring(2);
            oldNic = withOutYear.substring(0, 5).concat(newNIC.substring(7,12)).concat("V or X");
        } else {
            oldNic = newNIC;
        }
        return oldNic;
    }
}
