package com.all.shared.json;

import org.junit.Test;

import com.all.shared.stats.AboutUsStat;
import com.all.shared.stats.MediaContainerStat;
import com.all.shared.stats.MediaImportStat;
import com.all.shared.stats.PlayCountStat;
import com.all.shared.stats.SearchP2PWordStat;
import com.all.shared.stats.SkipCountStat;
import com.all.shared.stats.TrackRatingStat;
import com.all.shared.stats.UserLibraryOverview;
import com.all.shared.stats.UserSessionStat;
import com.all.shared.stats.UserSpecs;
import com.all.shared.stats.usage.UserActionStat;
import com.all.shared.util.JsonConverterPrimitiveCompliance;

public class TestAllStats {

	@Test
	public void shouldBeJsonConverterCompliant() throws Exception {
		JsonConverterPrimitiveCompliance.forClass(MediaContainerStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(MediaImportStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(UserActionStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(UserLibraryOverview.class).verify();
		JsonConverterPrimitiveCompliance.forClass(UserSessionStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(UserSpecs.class).verify();
		JsonConverterPrimitiveCompliance.forClass(AboutUsStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(PlayCountStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(SkipCountStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(TrackRatingStat.class).verify();
		JsonConverterPrimitiveCompliance.forClass(SearchP2PWordStat.class).verify();
	}
}
