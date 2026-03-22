package com.tripbook.app.data

import com.tripbook.app.R
import com.tripbook.app.data.model.Region
import com.tripbook.app.data.model.TouristSite

/**
 * SiteData is the single source of truth for all hardcoded regions and sites.
 *
 * IMAGE SETUP INSTRUCTIONS:
 * Add your Cameroonian site images to res/drawable/ with these exact names:
 *   img_littoral, img_southwest, img_centre, img_north, img_west  (region covers)
 *   img_douala_city, img_wouri_estuary, img_marche_central, img_bonanjo
 *   img_mount_cameroon, img_limbe_wildlife, img_limbe_beach, img_korup
 *   img_yaounde_city, img_national_museum, img_mvog_betsi, img_monastery
 *   img_waza, img_maroua, img_mandara, img_rhumsiki
 *   img_foumban, img_bafoussam, img_metche, img_bangante
 *
 * Until you add images, use R.drawable.ic_launcher_background as a placeholder.
 */
object SiteData {

    val regions: List<Region> = listOf(
        Region(
            id = "littoral",
            name = "Littoral",
            tagline = "Urban life, estuary & city energy",
            imageRes = R.drawable.img_littoral,
            description = "Cameroon's economic capital Douala sits in this vibrant coastal region, " +
                    "offering city tours, historic quarters, busy markets, and the iconic Wouri Estuary."
        ),
        Region(
            id = "southwest",
            name = "South West",
            tagline = "Nature, beaches & wildlife",
            imageRes = R.drawable.img_southwest,
            description = "From the summit of Mount Cameroon to the black sand beaches of Limbe " +
                    "and the dense jungle of Korup, this region is a paradise for nature lovers."
        ),
        Region(
            id = "centre",
            name = "Centre",
            tagline = "Capital city, culture & heritage",
            imageRes = R.drawable.img_centre,
            description = "Yaoundé, the political capital of Cameroon, anchors this region " +
                    "rich in museums, religious landmarks, and the famous Mvog-Betsi Zoo."
        ),
        Region(
            id = "north",
            name = "North",
            tagline = "Safari, Sahel & cultural diversity",
            imageRes = R.drawable.img_north,
            description = "Waza National Park's wildlife, the ancient Mandara Mountains, and " +
                    "the mystical landscape of Rhumsiki make the North unforgettable."
        ),
        Region(
            id = "west",
            name = "West",
            tagline = "Royal palaces, highlands & waterfalls",
            imageRes = R.drawable.img_west,
            description = "The Bamileke highlands are home to royal palaces, spectacular waterfalls, " +
                    "and some of Cameroon's most vibrant cultural traditions."
        )
    )

    val sites: List<TouristSite> = listOf(

        // ── LITTORAL ──────────────────────────────────────────────────────────
        TouristSite(
            id = "douala_city",
            name = "Douala City Tour",
            regionId = "littoral",
            description = "Explore Cameroon's largest city and economic hub. Discover the bustling " +
                    "port, the La Réunification monument, and the lively street life that makes " +
                    "Douala the beating heart of Cameroonian commerce.",
            imageRes = R.drawable.img_douala_city,
            lat = 4.0511, lng = 9.7679,
            rating = 4.2f,
            reviewCount = 38
        ),
        TouristSite(
            id = "wouri_estuary",
            name = "Wouri Estuary Cruise",
            regionId = "littoral",
            description = "A peaceful boat cruise along the Wouri Estuary offers stunning views " +
                    "of mangrove forests, local fishing villages, and the city skyline at sunset.",
            imageRes = R.drawable.img_wouri_estuary,
            lat = 4.0420, lng = 9.7010,
            rating = 4.5f,
            reviewCount = 24
        ),
        TouristSite(
            id = "marche_central",
            name = "Marché Central de Douala",
            regionId = "littoral",
            description = "The Grand Market of Douala is a sensory overload in the best way — " +
                    "fabrics, spices, crafts, and street food all in one sprawling, colourful space.",
            imageRes = R.drawable.img_marche_central,
            lat = 4.0596, lng = 9.7124,
            rating = 4.0f,
            reviewCount = 55
        ),
        TouristSite(
            id = "bonanjo",
            name = "Bonanjo Historic Quarter",
            regionId = "littoral",
            description = "Walk through the colonial-era administrative district of Douala, featuring " +
                    "preserved architecture, the Palace of Justice, and the city museum.",
            imageRes = R.drawable.img_bonanjo,
            lat = 4.0450, lng = 9.6980,
            rating = 3.9f,
            reviewCount = 19
        ),

        // ── SOUTH WEST ────────────────────────────────────────────────────────
        TouristSite(
            id = "mount_cameroon",
            name = "Mount Cameroon Hike",
            regionId = "southwest",
            description = "Africa's fourth-highest peak and an active volcano, Mount Cameroon " +
                    "offers multi-day hiking routes through cloud forests, lava fields, and up " +
                    "to 4,040 m of breathtaking altitude.",
            imageRes = R.drawable.img_mount_cameroon,
            lat = 4.2035, lng = 9.1703,
            rating = 4.8f,
            reviewCount = 72
        ),
        TouristSite(
            id = "limbe_wildlife",
            name = "Limbe Wildlife Centre",
            regionId = "southwest",
            description = "A rescue and rehabilitation centre for primates including chimpanzees, " +
                    "gorillas, and drills. An educational and moving experience for all ages.",
            imageRes = R.drawable.img_limbe_wildlife,
            lat = 4.0210, lng = 9.2018,
            rating = 4.6f,
            reviewCount = 44
        ),
        TouristSite(
            id = "limbe_beach",
            name = "Limbe Black Sand Beach",
            regionId = "southwest",
            description = "Unique volcanic black sand beaches stretch along Limbe's coast. " +
                    "Swim, relax, and enjoy fresh grilled fish against the backdrop of Mount Cameroon.",
            imageRes = R.drawable.img_limbe_beach,
            lat = 4.0178, lng = 9.2052,
            rating = 4.4f,
            reviewCount = 61
        ),
        TouristSite(
            id = "korup",
            name = "Korup National Park",
            regionId = "southwest",
            description = "One of Africa's oldest and most biodiverse rainforests. " +
                    "Home to 400+ bird species, 160 mammals, and ancient trees over 3,000 years old.",
            imageRes = R.drawable.img_korup,
            lat = 5.0500, lng = 8.8500,
            rating = 4.7f,
            reviewCount = 31
        ),

        // ── CENTRE ────────────────────────────────────────────────────────────
        TouristSite(
            id = "yaounde_city",
            name = "Yaoundé City Tour",
            regionId = "centre",
            description = "Discover the political capital of Cameroon — seven hills, " +
                    "the National Assembly, bustling Mokolo market, and the Reunification Palace.",
            imageRes = R.drawable.img_yaounde_city,
            lat = 3.8480, lng = 11.5021,
            rating = 4.1f,
            reviewCount = 42
        ),
        TouristSite(
            id = "national_museum",
            name = "National Museum of Yaoundé",
            regionId = "centre",
            description = "Housed in the former Presidential Palace, this museum showcases " +
                    "Cameroon's rich cultural diversity through traditional costumes, weapons, " +
                    "royal artefacts, and ethnographic collections.",
            imageRes = R.drawable.img_national_museum,
            lat = 3.8675, lng = 11.5167,
            rating = 4.3f,
            reviewCount = 28
        ),
        TouristSite(
            id = "mvog_betsi",
            name = "Mvog-Betsi Zoo",
            regionId = "centre",
            description = "Yaoundé's beloved zoo and botanical garden, home to lions, elephants, " +
                    "and rare forest species. A favourite family outing in the capital.",
            imageRes = R.drawable.img_mvog_betsi,
            lat = 3.8412, lng = 11.5089,
            rating = 4.0f,
            reviewCount = 50
        ),
        TouristSite(
            id = "monastery",
            name = "Benedictine Monastery of Mbalmayo",
            regionId = "centre",
            description = "A peaceful Benedictine monastery 45 km south of Yaoundé, offering " +
                    "guided tours, handmade honey products, and a profound sense of calm " +
                    "amid lush forest surroundings.",
            imageRes = R.drawable.img_monastery,
            lat = 3.5200, lng = 11.5000,
            rating = 4.5f,
            reviewCount = 16
        ),

        // ── NORTH ─────────────────────────────────────────────────────────────
        TouristSite(
            id = "waza",
            name = "Waza National Park",
            regionId = "north",
            description = "Cameroon's premier safari destination. Spot elephants, lions, giraffes, " +
                    "and hundreds of bird species across the Sahel savanna. Best visited " +
                    "November through April.",
            imageRes = R.drawable.img_waza,
            lat = 11.4000, lng = 14.4500,
            rating = 4.7f,
            reviewCount = 53
        ),
        TouristSite(
            id = "maroua",
            name = "Maroua Cultural Tour",
            regionId = "north",
            description = "The gateway to the Far North, Maroua is famous for its traditional " +
                    "leather crafts, bronze work, and the Grand Mosque. The Friday market is unmissable.",
            imageRes = R.drawable.img_maroua,
            lat = 10.5942, lng = 14.3247,
            rating = 4.2f,
            reviewCount = 35
        ),
        TouristSite(
            id = "mandara",
            name = "Mandara Mountains",
            regionId = "north",
            description = "A dramatic volcanic range dotted with Mafa and Mofu villages, " +
                    "terraced farms, and ancient rock art. Trekking here feels like stepping " +
                    "back through centuries of history.",
            imageRes = R.drawable.img_mandara,
            lat = 10.8000, lng = 13.8000,
            rating = 4.6f,
            reviewCount = 22
        ),
        TouristSite(
            id = "rhumsiki",
            name = "Rhumsiki Village & Landscape",
            regionId = "north",
            description = "Perhaps Cameroon's most photographed landscape — jagged volcanic " +
                    "peaks, the famous crab sorcerer, and a traditional Kapsiki village make " +
                    "this a surreal and unforgettable destination.",
            imageRes = R.drawable.img_rhumsiki,
            lat = 10.6167, lng = 13.7167,
            rating = 4.9f,
            reviewCount = 40
        ),

        // ── WEST ──────────────────────────────────────────────────────────────
        TouristSite(
            id = "foumban",
            name = "Foumban Royal Palace & Museum",
            regionId = "west",
            description = "The Palace of the Sultans of Bamoun is one of Cameroon's greatest " +
                    "cultural treasures, with the attached museum housing royal regalia, " +
                    "unique Bamoun script manuscripts, and centuries of dynastic history.",
            imageRes = R.drawable.img_foumban,
            lat = 5.7333, lng = 10.9000,
            rating = 4.8f,
            reviewCount = 47
        ),
        TouristSite(
            id = "bafoussam",
            name = "Bafoussam Cultural Tour",
            regionId = "west",
            description = "The capital of the West region offers lively markets, traditional " +
                    "Bamileke chief palaces, and excellent highland cuisine. " +
                    "Coffee and tea plantations surround the city.",
            imageRes = R.drawable.img_bafoussam,
            lat = 5.4764, lng = 10.4214,
            rating = 4.1f,
            reviewCount = 29
        ),
        TouristSite(
            id = "metche",
            name = "Chutes de la Métché",
            regionId = "west",
            description = "Stunning waterfalls cascading through lush highland vegetation near " +
                    "Dschang. A refreshing, photogenic escape from the city — popular with hikers " +
                    "and picnickers.",
            imageRes = R.drawable.img_metche,
            lat = 5.4400, lng = 10.0600,
            rating = 4.5f,
            reviewCount = 33
        ),
        TouristSite(
            id = "bangante",
            name = "Bangante & Bamileke Villages",
            regionId = "west",
            description = "Explore authentic Bamileke chiefdoms and traditional compound " +
                    "architecture. Village tours reveal ancestral masks, storytelling, " +
                    "and the living traditions of one of Cameroon's most dynamic ethnic groups.",
            imageRes = R.drawable.img_bangante,
            lat = 5.1333, lng = 10.9833,
            rating = 4.4f,
            reviewCount = 18
        )
    )

    /** Returns all sites for a given region ID */
    fun getSitesByRegion(regionId: String): List<TouristSite> =
        sites.filter { it.regionId == regionId }

    /** Returns a single site by ID */
    fun getSiteById(siteId: String): TouristSite? =
        sites.find { it.id == siteId }

    /** Returns a single region by ID */
    fun getRegionById(regionId: String): Region? =
        regions.find { it.id == regionId }

    /** Search sites by name or description — used in ExploreFragment */
    fun searchSites(query: String): List<TouristSite> {
        val q = query.lowercase().trim()
        return if (q.isEmpty()) sites
        else sites.filter {
            it.name.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.regionId.lowercase().contains(q)
        }
    }
}