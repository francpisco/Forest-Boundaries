package almeida.francisco.forestboundaries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import almeida.francisco.forestboundaries.model.MyMarker;
import almeida.francisco.forestboundaries.model.Owner;
import almeida.francisco.forestboundaries.model.Property;
import almeida.francisco.forestboundaries.model.Reading;
import almeida.francisco.forestboundaries.service.OwnerService;
import almeida.francisco.forestboundaries.service.PropertyService;
import almeida.francisco.forestboundaries.util.MapUtil;

public class PropertyDetailFragment
        extends Fragment implements OnMapReadyCallback{

    private long propertyId = 0;
    private Property property;
    private Fragment mapFragment;
    private GoogleMap map;
    private Bitmap mapBitmap;

    public PropertyDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            propertyId = savedInstanceState.getLong("property_id");
        }

        LatLng latLng = new LatLng(39.979, -8.7508);
        mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .camera(new CameraPosition(latLng, 18.0f, 0.0f, 0.0f)));
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.map_container, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        return inflater.inflate(R.layout.fragment_property_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        PropertyService propertyService = new PropertyService(getActivity());
        property = propertyService.findById(propertyId);

        ((SupportMapFragment) mapFragment).getMapAsync(this);

        Owner o = property.getOwner();
        if (view != null) {
            TextView ownerView = (TextView) view.findViewById(R.id.owner_value);
            ownerView.setText(o.toString());

            TextView descriptionView = (TextView) view.findViewById(R.id.description_value);
            descriptionView.setText(property.getLocationAndDescription());

            TextView firstMarkerView = (TextView) view.findViewById(R.id.marker_value);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        onSavedInstanceState.putLong("property_id", propertyId);
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapUtil.centerMap(property, map);
        MapUtil.drawPolygonFromReadings(property, map, true);
    }

    private class MyPrintDocumentAdapter extends PrintDocumentAdapter {

        private PrintedPdfDocument pdfDocument;

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle bundle) {

            pdfDocument = new PrintedPdfDocument(getActivity(), newAttributes);
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            PrintDocumentInfo info = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1)
                    .build();
            callback.onLayoutFinished(info,true);
        }

        @Override
        public void onWrite(PageRange[] pageRanges,
                            ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal,
                            WriteResultCallback callback) {

            PdfDocument.Page page = pdfDocument.startPage(0);
            if (cancellationSignal.isCanceled()) {
                pdfDocument.close();
                pdfDocument = null;
                callback.onWriteCancelled();
                return;
            }
            drawPage(page);
            pdfDocument.finishPage(page);

            try {
                pdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                pdfDocument.close();
                pdfDocument = null;
            }
            callback.onWriteFinished(pageRanges);
        }

        private void drawPage(PdfDocument.Page page) {
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(30f);
            canvas.drawText("Hello!", 80, 80, paint);
            canvas.drawBitmap(mapBitmap, new Rect(0, 0, 1000, 2000),
                    new Rect(10, 80, 300, 500), null);
        }
    }

    public void printDocument() {
        map.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                mapBitmap = bitmap;
            }
        });
        PrintManager printManager = (PrintManager) getActivity()
                .getSystemService(Context.PRINT_SERVICE);
        String jobName = getActivity().getString(R.string.app_name) + " Document";
        if (printManager != null)
            printManager.print(jobName, new MyPrintDocumentAdapter(), null);
    }
}
