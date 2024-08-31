import java.io.*;
import java.util.Random;
import java.util.List;

class Review {
    private int reviewID;
    private User reviewUser;
    private String[] questions;
    private int[] ratings;
    private String reviewComment;

    public Review(User user, String[] questions) {
        this.reviewID = reviewID;
        this.reviewUser = user;
        this.questions = questions;
        this.ratings = new int[questions.length];
        this.reviewComment = "";
    }


    // Getter
    public int getReviewID() { return reviewID; }
    public User getReviewUser() { return reviewUser; }
    public String getReviewComment() { return reviewComment;}

    public void setRating(int index, int rating) {
        if (index >= 0 && index < ratings.length) {
            ratings[index] = rating;
        }
    }

    public void setComment(String comment) {
        this.reviewComment = comment;
    }

    public void addReview() {
        Random random = new Random();
        reviewID = random.nextInt(100000);

        // Save the new review ratings in the text file
        try (FileWriter fileWriter = new FileWriter("reviews.txt", true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write("Review ID: " + reviewID + "\n");
            writer.write("User: " + reviewUser.getName() + "\n");
            for (int i = 0; i < questions.length; i++) {
                writer.write(questions[i] + "\n");
                writer.write("Rating (1-5): " + ratings[i] + "\n");
            }
            writer.write("Comment: " + reviewComment + "\n");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the review: " + e.getMessage());
        }
    }

    public static double computeAverageRating(List<Review> reviews) {
        double totalRating = 0;
        int totalReviews = reviews.size();

        if (totalReviews == 0) {
            return 0; // No reviews yet
        }

        int totalRatings = 0;

        for (Review review : reviews) {
            for (int rating : review.ratings) {
                totalRating += rating;
                totalRatings++;
            }
        }

        return totalRating / totalRatings;
    }
}