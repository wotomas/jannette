package helper;

import boofcv.alg.feature.detect.template.TemplateMatching;
import boofcv.alg.feature.detect.template.TemplateMatchingIntensity;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.factory.feature.detect.template.FactoryTemplateMatching;
import boofcv.factory.feature.detect.template.TemplateScoreType;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.feature.Match;
import boofcv.struct.image.Color3_F32;
import boofcv.struct.image.GrayF32;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of how to find objects inside an image using template matching.  Template matching works
 * well when there is little noise in the image and the object's appearance is known and static.  It can
 * also be very slow to compute, depending on the image and template size.
 *
 * @author Peter Abeles
 * @modified Jimmy Kim
 */
public class ObjectFinder {

    /**
     * Demonstrates how to search for matches of a template inside an image
     *
     * @param image           Image being searched
     * @param template        Template being looked for
     * @param mask            Mask which determines the weight of each template pixel in the match score
     * @param expectedMatches Number of expected matches it hopes to find
     * @return List of match location and scores
     */
    private List<Match> findMatches(GrayF32 image, GrayF32 template, GrayF32 mask, int expectedMatches, double threshold) {
        // create template matcher.
        TemplateMatching<GrayF32> matcher = FactoryTemplateMatching.createMatcher(TemplateScoreType.SUM_DIFF_SQ, GrayF32.class);

        // Find the points which match the template the best
        matcher.setTemplate(template, mask,expectedMatches);
        matcher.process(image);

        double point = matcher.getResults().get(0).score * -1;
        double thresholdBottom = threshold * 0.999999;
        double thresholdTop = threshold * 1.000001;
        System.out.println("Output the score : " + point + " bottom " + thresholdBottom + " top " + thresholdTop);
        //1.45 seems to be an adequate threshold
        if(point >= thresholdBottom && point <= thresholdTop) {
            return matcher.getResults().toList();
        } else {
            return null;
        }
    }

    public boolean find(String inputImage, String template, String mask, String outputImage, double threshold) {
        BufferedImage colorInput = UtilImageIO.loadImage(inputImage);
        // Load image and templates
        GrayF32 image = UtilImageIO.loadImage(inputImage, GrayF32.class);
        GrayF32 templateCursor = UtilImageIO.loadImage(template, GrayF32.class);
        GrayF32 maskCursor = UtilImageIO.loadImage(mask, GrayF32.class);

        // create output image to show results
        BufferedImage output = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB);
        ConvertBufferedImage.convertTo(image, output);
        Graphics2D g2 = output.createGraphics();
        Graphics2D g2Output = colorInput.createGraphics();

        //with mask
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));

        g2Output.setColor(Color.RED);
        g2Output.setStroke(new BasicStroke(5));
        try {
            List<Match> matches = drawRectangles(g2, image, templateCursor, maskCursor, 1, threshold);
            if(matches != null) {
                colorDrawBox(matches, templateCursor, g2Output);
                ImageIO.write(colorInput, "png", new File(outputImage));
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Can not find image. Try Next Video!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image unable to output!");
        }

        return false;
        /*
        //without mask
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2));
        drawRectangles(g2, image, templateCursor, null, 1);
        */
    }


    /**
     * Helper function will is finds matches and displays the results as colored rectangles
     */
    private List<Match> drawRectangles(Graphics2D g2,
                                            GrayF32 image, GrayF32 template, GrayF32 mask,
                                            int expectedMatches,
                                            double threshold) {
        List<Match> found = findMatches(image, template, mask, expectedMatches, threshold);
        return found;
    }

    private boolean colorDrawBox(List<Match> found, GrayF32 template, Graphics2D g2) {
        if(found == null) {
            return false;
        }

        int r = 2;
        int w = template.width + 2 * r;
        int h = template.height + 2 * r;

        for (Match m : found) {
            // the return point is the template's top left corner
            int x0 = m.x - r;
            int y0 = m.y - r;
            int x1 = x0 + w;
            int y1 = y0 + h;

            g2.drawLine(x0, y0, x1, y0);
            g2.drawLine(x1, y0, x1, y1);
            g2.drawLine(x1, y1, x0, y1);
            g2.drawLine(x0, y1, x0, y0);
        }

        return true;
    }
}