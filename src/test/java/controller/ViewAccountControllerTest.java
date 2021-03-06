package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.StringBuilder;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class ViewAccountControllerTest {
    @Test
    void testMenuAsAdmin () {
        final String output = getOutput(
            "1111", "1234"
        );

        final Pattern pattern = Pattern.compile(
            "^[1-9]+ - View Account Number", 
            Pattern.MULTILINE
        );
        assertTrue(pattern.matcher(output).find());
    }

    @Test
    void testMenuAsNonAdmin () {
        final String output = getOutput(
            "1234", "4321"
        );

        final Pattern pattern = Pattern.compile(
            "^[1-9]+ - View Account Number", 
            Pattern.MULTILINE
        );
        assertFalse(pattern.matcher(output).find());
    }

    @Test
    void testMenuView () {
        final String output = getOutput(
            "1111", "1234", "6"
        );

        final String expected = new StringBuilder()
            .append("1- Account Number: 1234\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("2- Account Number: 1235\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("3- Account Number: 8765\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("4- Account Number: 2205\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number blocked\n\n")
            .append("5- Account Number: 1111\n")
            .append(" - Role Account: admin\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .toString();
        final Pattern pattern = Pattern.compile(expected);
        assertTrue(pattern.matcher(output).find());
    }

    @Test
    void testMenuExit () {
        final String output = getOutput(
            "1111", "1234", "6", "A"
        );

        final String expected = new StringBuilder()
            .append("1- Account Number: 1234\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("2- Account Number: 1235\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("3- Account Number: 8765\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("4- Account Number: 2205\n")
            .append(" - Role Account: nasabah\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number blocked\n\n")
            .append("5- Account Number: 1111\n")
            .append(" - Role Account: admin\n")
            .append(" - Description Account Number: -\n")
            .append(" - Status Account Number: account number active\n\n")
            .append("----------------------------------------:\n\n")
            .append("Press Any Key\n\n")
            .append("Main menu admin:\n")
            .toString();
        final Pattern pattern = Pattern.compile(expected);
        assertTrue(pattern.matcher(output).find());
    }

    private String getOutput(String... inputs) {
        final PrintStream oldOut = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(
            (String.join("\n", inputs) + "\n").getBytes())
        );
        System.setOut(new PrintStream(out));

        try {
            ATM theATM = new ATM();
            theATM.run();
        }
        catch (Exception e) {
            // NOOP
        } finally {
            System.setOut(oldOut);
        }

        return out.toString().replaceAll("\r\n", "\n");
    }
}
