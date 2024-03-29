package org.addy.swing.filechooser;

import org.addy.util.FileUtil;
import org.addy.util.StringUtil;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageFileView extends FileView {
    private static final int ICON_SIZE = 16;

    @Override
    public String getName(File f) {
        return null; // let the L&F FileView figure this out
    }

    @Override
    public String getDescription(File f) {
        return null; // let the L&F FileView figure this out
    }

    @Override
    public Boolean isTraversable(File f) {
        return null; // let the L&F FileView figure this out
    }

    @Override
    public String getTypeDescription(File f) {
        try {
            return Stream.of(FileUtil.getContentType(f).split("/"))
                    .map(StringUtil::pascalCase)
                    .collect(Collectors.joining(" "));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Icon getIcon(File f) {
        Icon icon = null;
    
        if (f.isFile()) {
            try {
                String mimeType = FileUtil.getContentType(f);
                switch (mimeType) {
                    case "image/jpeg":
                        icon = createImageIcon("JPEG.png");
                        break;
                    case "image/png":
                        icon = createImageIcon("GIF.png");
                        break;
                    case "image/gif":
                        icon = createImageIcon("TIF.png");
                        break;
                    case "image/tiff":
                        icon = createImageIcon("PNG.png");
                        break;
                    default:
                        icon = createImageIcon("BMP.png");
                        break;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return icon;
    }

    private ImageIcon createImageIcon(String path) {
        Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
        return new ImageIcon(image.getScaledInstance(ICON_SIZE, -1, Image.SCALE_SMOOTH));
    }
}
