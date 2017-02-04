package nl.oose.dea.videostore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VideoStoreTest {
    private static final double DELTA = 0.01;
    private RentalStatement statement;
    private Movie newRelease1;
    private Movie newRelease2;
    private Movie childrens;
    private Movie regular1;
    private Movie regular2;
    private Movie regular3;


    @Before
    public void setUp() {
        statement = new RentalStatement("nl.oose.dea.videostore.Customer Name");
        newRelease1 = new NewReleaseMovie("New Release 1");
        newRelease2 = new NewReleaseMovie("New Release 2");
        childrens = new ChildrensMovie("Childrens");
        regular1 = new RegularMovie("Regular 1");
        regular2 = new RegularMovie("Regular 2");
        regular3 = new RegularMovie("Regular 3");
    }

    private void assertAmountAndPointsForReport(double expectedAmount, int expectedPoints) {
        assertEquals(expectedAmount, statement.getAmountOwed(), DELTA);
        assertEquals(expectedPoints, statement.getFrequentRenterPoints());
    }

    @Test
    public void testSingleNewReleaseStatement() {
        statement.addRental(new Rental(newRelease1, 3));
        statement.makeRentalStatement();
        assertAmountAndPointsForReport(9.0, 2);
    }

    @Test
    public void testDualNewReleaseStatement() {
        statement.addRental(new Rental(newRelease1, 3));
        statement.addRental(new Rental(newRelease2, 3));
        statement.makeRentalStatement();
        assertAmountAndPointsForReport(18.0, 4);
    }

    @Test
    public void testSingleChildrensStatement() {
        statement.addRental(new Rental(childrens, 3));
        statement.makeRentalStatement();
        assertAmountAndPointsForReport(1.5, 1);
    }

    @Test
    public void testMultipleRegularStatement() {
        statement.addRental(new Rental(regular1, 1));
        statement.addRental(new Rental(regular2, 2));
        statement.addRental(new Rental(regular3, 3));
        statement.makeRentalStatement();
        assertAmountAndPointsForReport(7.5, 3);
    }

    @Test
    public void testRentalStatementFormat() {
        statement.addRental(new Rental(regular1, 1));
        statement.addRental(new Rental(regular2, 2));
        statement.addRental(new Rental(regular3, 3));

        assertEquals(
                "nl.oose.dea.videostore.Rental Record for nl.oose.dea.videostore.Customer Name\n" +
                        "\tRegular 1\t2.0\n" +
                        "\tRegular 2\t2.0\n" +
                        "\tRegular 3\t3.5\n" +
                        "You owed 7.5\n" +
                        "You earned 3 frequent renter points\n",
                statement.makeRentalStatement());
    }
}