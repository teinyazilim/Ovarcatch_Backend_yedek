package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.*;
import com.tein.overcatchbackend.mapper.*;
import com.tein.overcatchbackend.repository.*;
import com.tein.overcatchbackend.util.GlobalVariable;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor

public class CreateMockDataService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final NatureBusinessRepository natureBusinessRepository;
    private final IncorprationCompanyRepository incorprationCompanyRepository;
    private final DirectorDetailRepository directorDetailRepository;
    private final FounderOwnerRepository founderOwnerRepository;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final RoleMapper roleMapper;
    private final DirectorDetailMapper directorDetailMapper;
    private final ClientMapper clientMapper;
    private final AddressMapper addressMapper;
    private final CompanyMapper incorprationCompanyMapper;
    private final FounderOwnerMapper founderOwnerMapper;
    private final UserMapper userMapper;
    private final JdbcTemplate jdbcTemplate;
    private final UserCompanyMapper userCompanyMapper;
    private final NatureBusinessMapper natureBusinessMapper;
    private final DocumentMapper documentMapper;
    private final DocumentRepository documentRepository;
    private final CustomerRepository customerRepository;
    private final PersonelRepository personelRepository;
    private final ModuleTypeRepository moduleTypeRepository;
    private final HelpTypeRepository helpTypeRepository;
    List<String> natureBusiness = new ArrayList<String>();
    private final PasswordEncoder passwordEncoder;

    public void createMockData() {
//        String[] time_zone = {
//                "SET GLOBAL time_zone = '+0:00'",
//                "SET @@global.time_zone = '+00:00'"};
//        for (String a : time_zone) {
//            jdbcTemplate.execute(a);
//        }

        natureBusiness.addAll(Arrays.asList(
//                "Section A#Agriculture, Forestry and Fishing",
                "01110#Growing of cereals (except rice), leguminous crops and oil seeds",
                "01120#Growing of rice",
                "01130#Growing of vegetables and melons, roots and tubers",
                "01140#Growing of sugar cane",
                "01150#Growing of tobacco",
                "01160#Growing of fibre crops",
                "01190#Growing of other non-perennial crops",
                "01210#Growing of grapes",
                "01220#Growing of tropical and subtropical fruits",
                "01230#Growing of citrus fruits",
                "01240#Growing of pome fruits and stone fruits",
                "01250#Growing of other tree and bush fruits and nuts",
                "01260#Growing of oleaginous fruits",
                "01270#Growing of beverage crops",
                "01280#Growing of spices, aromatic, drug and pharmaceutical crops",
                "01290#Growing of other perennial crops",
                "01300#Plant propagation",
                "01410#Raising of dairy cattle",
                "01420#Raising of other cattle and buffaloes",
                "01430#Raising of horses and other equines",
                "01440#Raising of camels and camelids",
                "01450#Raising of sheep and goats",
                "01460#Raising of swine/pigs",
                "01470#Raising of poultry",
                "01490#Raising of other animals",
                "01500#Mixed farming",
                "01610#Support activities for crop production",
                "01621#Farm animal boarding and care",
                "01629#Support activities for animal production (other than farm animal boarding and care) n.e.c.",
                "01630#Post-harvest crop activities",
                "01640#Seed processing for propagation",
                "01700#Hunting, trapping and related service activities",
                "02100#Silviculture and other forestry activities",
                "02200#Logging",
                "02300#Gathering of wild growing non-wood products",
                "02400#Support services to forestry",
                "03110#Marine fishing",
                "03120#Freshwater fishing",
                "03210#Marine aquaculture",
                "03220#Freshwater aquaculture",


//                "Section B#Mining and Quarrying",
                "05101#Deep coal mines",
                "05102#Open cast coal working",
                "05200#Mining of lignite",
                "06100#Extraction of crude petroleum",
                "06200#Extraction of natural gas",
                "07100#Mining of iron ores",
                "07210#Mining of uranium and thorium ores",
                "07290#Mining of other non-ferrous metal ores",
                "08110#Quarrying of ornamental and building stone, limestone, gypsum, chalk and slate",
                "08120#Operation of gravel and sand pits; mining of clays and kaolin",
                "08910#Mining of chemical and fertilizer minerals",
                "08920#Extraction of peat",
                "08930#Extraction of salt",
                "08990#Other mining and quarrying n.e.c.",
                "09100#Support activities for petroleum and natural gas mining",
                "09900#Support activities for other mining and quarrying",


//                "Section C#Manufacturing",
                "10110#Processing and preserving of meat",
                "10120#Processing and preserving of poultry meat",
                "10130#Production of meat and poultry meat products",
                "10200#Processing and preserving of fish, crustaceans and molluscs",
                "10310#Processing and preserving of potatoes",
                "10320#Manufacture of fruit and vegetable juice",
                "10390#Other processing and preserving of fruit and vegetables",
                "10410#Manufacture of oils and fats",
                "10420#Manufacture of margarine and similar edible fats",
                "10511#Liquid milk and cream production",
                "10512#Butter and cheese production",
                "10519#Manufacture of other milk products",
                "10520#Manufacture of ice cream",
                "10611#Grain milling",
                "10612#Manufacture of breakfast cereals and cereals-based food",
                "10620#Manufacture of starches and starch products",
                "10710#Manufacture of bread; manufacture of fresh pastry goods and cakes",
                "10720#Manufacture of rusks and biscuits; manufacture of preserved pastry goods and cakes",
                "10730#Manufacture of macaroni, noodles, couscous and similar farinaceous products",
                "10810#Manufacture of sugar",
                "10821#Manufacture of cocoa and chocolate confectionery",
                "10822#Manufacture of sugar confectionery",
                "10831#Tea processing",
                "10832#Production of coffee and coffee substitutes",
                "10840#Manufacture of condiments and seasonings",
                "10850#Manufacture of prepared meals and dishes",
                "10860#Manufacture of homogenized food preparations and dietetic food",
                "10890#Manufacture of other food products n.e.c.",
                "10910#Manufacture of prepared feeds for farm animals",
                "10920#Manufacture of prepared pet foods",
                "11010#Distilling, rectifying and blending of spirits",
                "11020#Manufacture of wine from grape",
                "11030#Manufacture of cider and other fruit wines",
                "11040#Manufacture of other non-distilled fermented beverages",
                "11050#Manufacture of beer",
                "11060#Manufacture of malt",
                "11070#Manufacture of soft drinks; production of mineral waters and other bottled waters",
                "12000#Manufacture of tobacco products",
                "13100#Preparation and spinning of textile fibres",
                "13200#Weaving of textiles",
                "13300#Finishing of textiles",
                "13910#Manufacture of knitted and crocheted fabrics",
                "13921#Manufacture of soft furnishings",
                "13922#manufacture of canvas goods, sacks, etc.",
                "13923#manufacture of household textiles",
                "13931#Manufacture of woven or tufted carpets and rugs",
                "13939#Manufacture of other carpets and rugs",
                "13940#Manufacture of cordage, rope, twine and netting",
                "13950#Manufacture of non-wovens and articles made from non-wovens, except apparel",
                "13960#Manufacture of other technical and industrial textiles",
                "13990#Manufacture of other textiles n.e.c.",
                "14110#Manufacture of leather clothes",
                "14120#Manufacture of workwear",
                "14131#Manufacture of other men's outerwear",
                "14132#Manufacture of other women's outerwear",
                "14141#Manufacture of men's underwear",
                "14142#Manufacture of women's underwear",
                "14190#Manufacture of other wearing apparel and accessories n.e.c.",
                "14200#Manufacture of articles of fur",
                "14310#Manufacture of knitted and crocheted hosiery",
                "14390#Manufacture of other knitted and crocheted apparel",
                "15110#Tanning and dressing of leather; dressing and dyeing of fur",
                "15120#Manufacture of luggage, handbags and the like, saddlery and harness",
                "15200#Manufacture of footwear",
                "16100#Sawmilling and planing of wood",
                "16210#Manufacture of veneer sheets and wood-based panels",
                "16220#Manufacture of assembled parquet floors",
                "16230#Manufacture of other builders' carpentry and joinery",
                "16240#Manufacture of wooden containers",
                "16290#Manufacture of other products of wood; manufacture of articles of cork, straw and plaiting materials",
                "17110#Manufacture of pulp",
                "17120#Manufacture of paper and paperboard",
                "17211#Manufacture of corrugated paper and paperboard, sacks and bags",
                "17219#Manufacture of other paper and paperboard containers",
                "17220#Manufacture of household and sanitary goods and of toilet requisites",
                "17230#Manufacture of paper stationery",
                "17240#Manufacture of wallpaper",
                "17290#Manufacture of other articles of paper and paperboard n.e.c.",
                "18110#Printing of newspapers",
                "18121#Manufacture of printed labels",
                "18129#Printing n.e.c.",
                "18130#Pre-press and pre-media services",
                "18140#Binding and related services",
                "18201#Reproduction of sound recording",
                "18202#Reproduction of video recording",
                "18203#Reproduction of computer media",
                "19100#Manufacture of coke oven products",
                "19201#Mineral oil refining",
                "19209#Other treatment of petroleum products (excluding petrochemicals manufacture)",
                "20110#Manufacture of industrial gases",
                "20120#Manufacture of dyes and pigments",
                "20130#Manufacture of other inorganic basic chemicals",
                "20140#Manufacture of other organic basic chemicals",
                "20150#Manufacture of fertilizers and nitrogen compounds",
                "20160#Manufacture of plastics in primary forms",
                "20170#Manufacture of synthetic rubber in primary forms",
                "20200#Manufacture of pesticides and other agrochemical products",
                "20301#Manufacture of paints, varnishes and similar coatings, mastics and sealants",
                "20302#Manufacture of printing ink",
                "20411#Manufacture of soap and detergents",
                "20412#Manufacture of cleaning and polishing preparations",
                "20420#Manufacture of perfumes and toilet preparations",
                "20510#Manufacture of explosives",
                "20520#Manufacture of glues",
                "20530#Manufacture of essential oils",
                "20590#Manufacture of other chemical products n.e.c.",
                "20600#Manufacture of man-made fibres",
                "21100#Manufacture of basic pharmaceutical products",
                "21200#Manufacture of pharmaceutical preparations",
                "22110#Manufacture of rubber tyres and tubes; retreading and rebuilding of rubber tyres",
                "22190#Manufacture of other rubber products",
                "22210#Manufacture of plastic plates, sheets, tubes and profiles",
                "22220#Manufacture of plastic packing goods",
                "22230#Manufacture of builders ware of plastic",
                "22290#Manufacture of other plastic products",
                "23110#Manufacture of flat glass",
                "23120#Shaping and processing of flat glass",
                "23130#Manufacture of hollow glass",
                "23140#Manufacture of glass fibres",
                "23190#Manufacture and processing of other glass, including technical glassware",
                "23200#Manufacture of refractory products",
                "23310#Manufacture of ceramic tiles and flags",
                "23320#Manufacture of bricks, tiles and construction products, in baked clay",
                "23410#Manufacture of ceramic household and ornamental articles",
                "23420#Manufacture of ceramic sanitary fixtures",
                "23430#Manufacture of ceramic insulators and insulating fittings",
                "23440#Manufacture of other technical ceramic products",
                "23490#Manufacture of other ceramic products n.e.c.",
                "23510#Manufacture of cement",
                "23520#Manufacture of lime and plaster",
                "23610#Manufacture of concrete products for construction purposes",
                "23620#Manufacture of plaster products for construction purposes",
                "23630#Manufacture of ready-mixed concrete",
                "23640#Manufacture of mortars",
                "23650#Manufacture of fibre cement",
                "23690#Manufacture of other articles of concrete, plaster and cement",
                "23700#Cutting, shaping and finishing of stone",
                "23910#Production of abrasive products",
                "23990#Manufacture of other non-metallic mineral products n.e.c.",
                "24100#Manufacture of basic iron and steel and of ferro-alloys",
                "24200#Manufacture of tubes, pipes, hollow profiles and related fittings, of steel",
                "24310#Cold drawing of bars",
                "24320#Cold rolling of narrow strip",
                "24330#Cold forming or folding",
                "24340#Cold drawing of wire",
                "24410#Precious metals production",
                "24420#Aluminium production",
                "24430#Lead, zinc and tin production",
                "24440#Copper production",
                "24450#Other non-ferrous metal production",
                "24460#Processing of nuclear fuel",
                "24510#Casting of iron",
                "24520#Casting of steel",
                "24530#Casting of light metals",
                "24540#Casting of other non-ferrous metals",
                "25110#Manufacture of metal structures and parts of structures",
                "25120#Manufacture of doors and windows of metal",
                "25210#Manufacture of central heating radiators and boilers",
                "25290#Manufacture of other tanks, reservoirs and containers of metal",
                "25300#Manufacture of steam generators, except central heating hot water boilers",
                "25400#Manufacture of weapons and ammunition",
                "25500#Forging, pressing, stamping and roll-forming of metal; powder metallurgy",
                "25610#Treatment and coating of metals",
                "25620#Machining",
                "25710#Manufacture of cutlery",
                "25720#Manufacture of locks and hinges",
                "25730#Manufacture of tools",
                "25910#Manufacture of steel drums and similar containers",
                "25920#Manufacture of light metal packaging",
                "25930#Manufacture of wire products, chain and springs",
                "25940#Manufacture of fasteners and screw machine products",
                "25990#Manufacture of other fabricated metal products n.e.c.",
                "26110#Manufacture of electronic components",
                "26120#Manufacture of loaded electronic boards",
                "26200#Manufacture of computers and peripheral equipment",
                "26301#Manufacture of telegraph and telephone apparatus and equipment",
                "26309#Manufacture of communication equipment other than telegraph, and telephone apparatus and equipment",
                "26400#Manufacture of consumer electronics",
                "26511#Manufacture of electronic measuring, testing etc. equipment, not for industrial process control",
                "26512#Manufacture of electronic industrial process control equipment",
                "26513#Manufacture of non-electronic measuring, testing etc. equipment, not for industrial process control",
                "26514#Manufacture of non-electronic industrial process control equipment",
                "26520#Manufacture of watches and clocks",
                "26600#Manufacture of irradiation, electromedical and electrotherapeutic equipment",
                "26701#Manufacture of optical precision instruments",
                "26702#Manufacture of photographic and cinematographic equipment",
                "26800#Manufacture of magnetic and optical media",
                "27110#Manufacture of electric motors, generators and transformers",
                "27120#Manufacture of electricity distribution and control apparatus",
                "27200#Manufacture of batteries and accumulators",
                "27310#Manufacture of fibre optic cables",
                "27320#Manufacture of other electronic and electric wires and cables",
                "27330#Manufacture of wiring devices",
                "27400#Manufacture of electric lighting equipment",
                "27510#Manufacture of electric domestic appliances",
                "27520#Manufacture of non-electric domestic appliances",
                "27900#Manufacture of other electrical equipment",
                "28110#Manufacture of engines and turbines, except aircraft, vehicle and cycle engines",
                "28120#Manufacture of fluid power equipment",
                "28131#Manufacture of pumps",
                "28132#Manufacture of compressors",
                "28140#Manufacture of taps and valves",
                "28150#Manufacture of bearings, gears, gearing and driving elements",
                "28210#Manufacture of ovens, furnaces and furnace burners",
                "28220#Manufacture of lifting and handling equipment",
                "28230#Manufacture of office machinery and equipment (except computers and peripheral equipment)",
                "28240#Manufacture of power-driven hand tools",
                "28250#Manufacture of non-domestic cooling and ventilation equipment",
                "28290#Manufacture of other general-purpose machinery n.e.c.",
                "28301#Manufacture of agricultural tractors",
                "28302#Manufacture of agricultural and forestry machinery other than tractors",
                "28410#Manufacture of metal forming machinery",
                "28490#Manufacture of other machine tools",
                "28910#Manufacture of machinery for metallurgy",
                "28921#Manufacture of machinery for mining",
                "28922#Manufacture of earthmoving equipment",
                "28923#Manufacture of equipment for concrete crushing and screening and roadworks",
                "28930#Manufacture of machinery for food, beverage and tobacco processing",
                "28940#Manufacture of machinery for textile, apparel and leather production",
                "28950#Manufacture of machinery for paper and paperboard production",
                "28960#Manufacture of plastics and rubber machinery",
                "28990#Manufacture of other special-purpose machinery n.e.c.",
                "29100#Manufacture of motor vehicles",
                "29201#Manufacture of bodies (coachwork) for motor vehicles (except caravans)",
                "29202#Manufacture of trailers and semi-trailers",
                "29203#Manufacture of caravans",
                "29310#Manufacture of electrical and electronic equipment for motor vehicles and their engines",
                "29320#Manufacture of other parts and accessories for motor vehicles",
                "30110#Building of ships and floating structures",
                "30120#Building of pleasure and sporting boats",
                "30200#Manufacture of railway locomotives and rolling stock",
                "30300#Manufacture of air and spacecraft and related machinery",
                "30400#Manufacture of military fighting vehicles",
                "30910#Manufacture of motorcycles",
                "30920#Manufacture of bicycles and invalid carriages",
                "30990#Manufacture of other transport equipment n.e.c.",
                "31010#Manufacture of office and shop furniture",
                "31020#Manufacture of kitchen furniture",
                "31030#Manufacture of mattresses",
                "31090#Manufacture of other furniture",
                "32110#Striking of coins",
                "32120#Manufacture of jewellery and related articles",
                "32130#Manufacture of imitation jewellery and related articles",
                "32200#Manufacture of musical instruments",
                "32300#Manufacture of sports goods",
                "32401#Manufacture of professional and arcade games and toys",
                "32409#Manufacture of other games and toys, n.e.c.",
                "32500#Manufacture of medical and dental instruments and supplies",
                "32910#Manufacture of brooms and brushes",
                "32990#Other manufacturing n.e.c.",
                "33110#Repair of fabricated metal products",
                "33120#Repair of machinery",
                "33130#Repair of electronic and optical equipment",
                "33140#Repair of electrical equipment",
                "33150#Repair and maintenance of ships and boats",
                "33160#Repair and maintenance of aircraft and spacecraft",
                "33170#Repair and maintenance of other transport equipment n.e.c.",
                "33190#Repair of other equipment",
                "33200#Installation of industrial machinery and equipment",


//                "Section D#Electricity, gas, steam and air conditioning supply",
                "35110#Production of electricity",
                "35120#Transmission of electricity",
                "35130#Distribution of electricity",
                "35140#Trade of electricity",
                "35210#Manufacture of gas",
                "35220#Distribution of gaseous fuels through mains",
                "35230#Trade of gas through mains",
                "35300#Steam and air conditioning supply",


//                "Section E#Water supply, sewerage, waste management and remediation activities",
                "36000#Water collection, treatment and supply",
                "37000#Sewerage",
                "38110#Collection of non-hazardous waste",
                "38120#Collection of hazardous waste",
                "38210#Treatment and disposal of non-hazardous waste",
                "38220#Treatment and disposal of hazardous waste",
                "38310#Dismantling of wrecks",
                "38320#Recovery of sorted materials",
                "39000#Remediation activities and other waste management services",


//                "Section F#Construction",
                "41100#Development of building projects",
                "41201#Construction of commercial buildings",
                "41202#Construction of domestic buildings",
                "42110#Construction of roads and motorways",
                "42120#Construction of railways and underground railways",
                "42130#Construction of bridges and tunnels",
                "42210#Construction of utility projects for fluids",
                "42220#Construction of utility projects for electricity and telecommunications",
                "42910#Construction of water projects",
                "42990#Construction of other civil engineering projects n.e.c.",
                "43110#Demolition",
                "43120#Site preparation",
                "43130#Test drilling and boring",
                "43210#Electrical installation",
                "43220#Plumbing, heat and air-conditioning installation",
                "43290#Other construction installation",
                "43310#Plastering",
                "43320#Joinery installation",
                "43330#Floor and wall covering",
                "43341#Painting",
                "43342#Glazing",
                "43390#Other building completion and finishing",
                "43910#Roofing activities",
                "43991#Scaffold erection",
                "43999#Other specialised construction activities n.e.c.",


//                "Section G#Wholesale and retail trade; repair of motor vehicles and motorcycles",
                "45111#Sale of new cars and light motor vehicles",
                "45112#Sale of used cars and light motor vehicles",
                "45190#Sale of other motor vehicles",
                "45200#Maintenance and repair of motor vehicles",
                "45310#Wholesale trade of motor vehicle parts and accessories",
                "45320#Retail trade of motor vehicle parts and accessories",
                "45400#Sale, maintenance and repair of motorcycles and related parts and accessories",
                "46110#Agents selling agricultural raw materials, livestock, textile raw materials and semi-finished goods",
                "46120#Agents involved in the sale of fuels, ores, metals and industrial chemicals",
                "46130#Agents involved in the sale of timber and building materials",
                "46140#Agents involved in the sale of machinery, industrial equipment, ships and aircraft",
                "46150#Agents involved in the sale of furniture, household goods, hardware and ironmongery",
                "46160#Agents involved in the sale of textiles, clothing, fur, footwear and leather goods",
                "46170#Agents involved in the sale of food, beverages and tobacco",
                "46180#Agents specialised in the sale of other particular products",
                "46190#Agents involved in the sale of a variety of goods",
                "46210#Wholesale of grain, unmanufactured tobacco, seeds and animal feeds",
                "46220#Wholesale of flowers and plants",
                "46230#Wholesale of live animals",
                "46240#Wholesale of hides, skins and leather",
                "46310#Wholesale of fruit and vegetables",
                "46320#Wholesale of meat and meat products",
                "46330#Wholesale of dairy products, eggs and edible oils and fats",
                "46341#Wholesale of fruit and vegetable juices, mineral water and soft drinks",
                "46342#Wholesale of wine, beer, spirits and other alcoholic beverages",
                "46350#Wholesale of tobacco products",
                "46360#Wholesale of sugar and chocolate and sugar confectionery",
                "46370#Wholesale of coffee, tea, cocoa and spices",
                "46380#Wholesale of other food, including fish, crustaceans and molluscs",
                "46390#Non-specialised wholesale of food, beverages and tobacco",
                "46410#Wholesale of textiles",
                "46420#Wholesale of clothing and footwear",
                "46431#Wholesale of audio tapes, records, CDs and video tapes and the equipment on which these are played",
                "46439#Wholesale of radio, television goods & electrical household appliances (other than records, tapes, CD's & video tapes and the equipment used for playing them)",
                "46440#Wholesale of china and glassware and cleaning materials",
                "46450#Wholesale of perfume and cosmetics",
                "46460#Wholesale of pharmaceutical goods",
                "46470#Wholesale of furniture, carpets and lighting equipment",
                "46480#Wholesale of watches and jewellery",
                "46491#Wholesale of musical instruments",
                "46499#Wholesale of household goods (other than musical instruments) n.e.c",
                "46510#Wholesale of computers, computer peripheral equipment and software",
                "46520#Wholesale of electronic and telecommunications equipment and parts",
                "46610#Wholesale of agricultural machinery, equipment and supplies",
                "46620#Wholesale of machine tools",
                "46630#Wholesale of mining, construction and civil engineering machinery",
                "46640#Wholesale of machinery for the textile industry and of sewing and knitting machines",
                "46650#Wholesale of office furniture",
                "46660#Wholesale of other office machinery and equipment",
                "46690#Wholesale of other machinery and equipment",
                "46711#Wholesale of petroleum and petroleum products",
                "46719#Wholesale of other fuels and related products",
                "46720#Wholesale of metals and metal ores",
                "46730#Wholesale of wood, construction materials and sanitary equipment",
                "46740#Wholesale of hardware, plumbing and heating equipment and supplies",
                "46750#Wholesale of chemical products",
                "46760#Wholesale of other intermediate products",
                "46770#Wholesale of waste and scrap",
                "46900#Non-specialised wholesale trade",
                "47110#Retail sale in non-specialised stores with food, beverages or tobacco predominating",
                "47190#Other retail sale in non-specialised stores",
                "47210#Retail sale of fruit and vegetables in specialised stores",
                "47220#Retail sale of meat and meat products in specialised stores",
                "47230#Retail sale of fish, crustaceans and molluscs in specialised stores",
                "47240#Retail sale of bread, cakes, flour confectionery and sugar confectionery in specialised stores",
                "47250#Retail sale of beverages in specialised stores",
                "47260#Retail sale of tobacco products in specialised stores",
                "47290#Other retail sale of food in specialised stores",
                "47300#Retail sale of automotive fuel in specialised stores",
                "47410#Retail sale of computers, peripheral units and software in specialised stores",
                "47421#Retail sale of mobile telephones",
                "47429#Retail sale of telecommunications equipment other than mobile telephones",
                "47430#Retail sale of audio and video equipment in specialised stores",
                "47510#Retail sale of textiles in specialised stores",
                "47520#Retail sale of hardware, paints and glass in specialised stores",
                "47530#Retail sale of carpets, rugs, wall and floor coverings in specialised stores",
                "47540#Retail sale of electrical household appliances in specialised stores",
                "47591#Retail sale of musical instruments and scores",
                "47599#Retail of furniture, lighting, and similar (not musical instruments or scores) in specialised store",
                "47610#Retail sale of books in specialised stores",
                "47620#Retail sale of newspapers and stationery in specialised stores",
                "47630#Retail sale of music and video recordings in specialised stores",
                "47640#Retail sale of sports goods, fishing gear, camping goods, boats and bicycles",
                "47650#Retail sale of games and toys in specialised stores",
                "47710#Retail sale of clothing in specialised stores",
                "47721#Retail sale of footwear in specialised stores",
                "47722#Retail sale of leather goods in specialised stores",
                "47730#Dispensing chemist in specialised stores",
                "47741#Retail sale of hearing aids",
                "47749#Retail sale of medical and orthopaedic goods in specialised stores (not incl. hearing aids) n.e.c.",
                "47750#Retail sale of cosmetic and toilet articles in specialised stores",
                "47760#Retail sale of flowers, plants, seeds, fertilizers, pet animals and pet food in specialised stores",
                "47770#Retail sale of watches and jewellery in specialised stores",
                "47781#Retail sale in commercial art galleries",
                "47782#Retail sale by opticians",
                "47789#Other retail sale of new goods in specialised stores (not commercial art galleries and opticians)",
                "47791#Retail sale of antiques including antique books in stores",
                "47799#Retail sale of other second-hand goods in stores (not incl. antiques)",
                "47810#Retail sale via stalls and markets of food, beverages and tobacco products",
                "47820#Retail sale via stalls and markets of textiles, clothing and footwear",
                "47890#Retail sale via stalls and markets of other goods",
                "47910#Retail sale via mail order houses or via Internet",
                "47990#Other retail sale not in stores, stalls or markets",


//                "Section H#Transportation and storage",
                "49100#Passenger rail transport, interurban",
                "49200#Freight rail transport",
                "49311#Urban and suburban passenger railway transportation by underground, metro and similar systems",
                "49319#Other urban, suburban or metropolitan passenger land transport (not underground, metro or similar)",
                "49320#Taxi operation",
                "49390#Other passenger land transport",
                "49410#Freight transport by road",
                "49420#Removal services",
                "49500#Transport via pipeline",
                "50100#Sea and coastal passenger water transport",
                "50200#Sea and coastal freight water transport",
                "50300#Inland passenger water transport",
                "50400#Inland freight water transport",
                "51101#Scheduled passenger air transport",
                "51102#Non-scheduled passenger air transport",
                "51210#Freight air transport",
                "51220#Space transport",
                "52101#Operation of warehousing and storage facilities for water transport activities",
                "52102#Operation of warehousing and storage facilities for air transport activities",
                "52103#Operation of warehousing and storage facilities for land transport activities",
                "52211#Operation of rail freight terminals",
                "52212#Operation of rail passenger facilities at railway stations",
                "52213#Operation of bus and coach passenger facilities at bus and coach stations",
                "52219#Other service activities incidental to land transportation, n.e.c.",
                "52220#Service activities incidental to water transportation",
                "52230#Service activities incidental to air transportation",
                "52241#Cargo handling for water transport activities",
                "52242#Cargo handling for air transport activities",
                "52243#Cargo handling for land transport activities",
                "52290#Other transportation support activities",
                "53100#Postal activities under universal service obligation",
                "53201#Licensed carriers",
                "53202#Unlicensed carriers",


//                "Section I#Accommodation and food service activities",
                "55100#Hotels and similar accommodation",
                "55201#Holiday centres and villages",
                "55202#Youth hostels",
                "55209#Other holiday and other collective accommodation",
                "55300#Recreational vehicle parks, trailer parks and camping grounds",
                "55900#Other accommodation",
                "56101#Licenced restaurants",
                "56102#Unlicenced restaurants and cafes",
                "56103#Take-away food shops and mobile food stands",
                "56210#Event catering activities",
                "56290#Other food services",
                "56301#Licenced clubs",
                "56302#Public houses and bars",


//                "Section J#Information and communication",
                "58110#Book publishing",
                "58120#Publishing of directories and mailing lists",
                "58130#Publishing of newspapers",
                "58141#Publishing of learned journals",
                "58142#Publishing of consumer and business journals and periodicals",
                "58190#Other publishing activities",
                "58210#Publishing of computer games",
                "58290#Other software publishing",
                "59111#Motion picture production activities",
                "59112#Video production activities",
                "59113#Television programme production activities",
                "59120#Motion picture, video and television programme post-production activities",
                "59131#Motion picture distribution activities",
                "59132#Video distribution activities",
                "59133#Television programme distribution activities",
                "59140#Motion picture projection activities",
                "59200#Sound recording and music publishing activities",
                "60100#Radio broadcasting",
                "60200#Television programming and broadcasting activities",
                "61100#Wired telecommunications activities",
                "61200#Wireless telecommunications activities",
                "61300#Satellite telecommunications activities",
                "61900#Other telecommunications activities",
                "62011#Ready-made interactive leisure and entertainment software development",
                "62012#Business and domestic software development",
                "62020#Information technology consultancy activities",
                "62030#Computer facilities management activities",
                "62090#Other information technology service activities",
                "63110#Data processing, hosting and related activities",
                "63120#Web portals",
                "63910#News agency activities",
                "63990#Other information service activities n.e.c.",


//                "Section K#Financial and insurance activities",
                "64110#Central banking",
                "64191#Banks",
                "64192#Building societies",
                "64201#Activities of agricultural holding companies",
                "64202#Activities of production holding companies",
                "64203#Activities of construction holding companies",
                "64204#Activities of distribution holding companies",
                "64205#Activities of financial services holding companies",
                "64209#Activities of other holding companies n.e.c.",
                "64301#Activities of investment trusts",
                "64302#Activities of unit trusts",
                "64303#Activities of venture and development capital companies",
                "64304#Activities of open-ended investment companies",
                "64305#Activities of property unit trusts",
                "64306#Activities of real estate investment trusts",
                "64910#Financial leasing",
                "64921#Credit granting by non-deposit taking finance houses and other specialist consumer credit grantors",
                "64922#Activities of mortgage finance companies",
                "64929#Other credit granting n.e.c.",
                "64991#Security dealing on own account",
                "64992#Factoring",
                "64999#Financial intermediation not elsewhere classified",
                "65110#Life insurance",
                "65120#Non-life insurance",
                "65201#Life reinsurance",
                "65202#Non-life reinsurance",
                "65300#Pension funding",
                "66110#Administration of financial markets",
                "66120#Security and commodity contracts dealing activities",
                "66190#Activities auxiliary to financial intermediation n.e.c.",
                "66210#Risk and damage evaluation",
                "66220#Activities of insurance agents and brokers",
                "66290#Other activities auxiliary to insurance and pension funding",
                "66300#Fund management activities",


//                "Section L#Real estate activities",
                "68100#Buying and selling of own real estate",
                "68201#Renting and operating of Housing Association real estate",
                "68202#Letting and operating of conference and exhibition centres",
                "68209#Other letting and operating of own or leased real estate",
                "68310#Real estate agencies",
                "68320#Management of real estate on a fee or contract basis",


//                "Section M#Professional, scientific and technical activities",
                "69101#Barristers at law",
                "69102#Solicitors",
                "69109#Activities of patent and copyright agents; other legal activities n.e.c.",
                "69201#Accounting and auditing activities",
                "69202#Bookkeeping activities",
                "69203#Tax consultancy",
                "70100#Activities of head offices",
                "70210#Public relations and communications activities",
                "70221#Financial management",
                "70229#Management consultancy activities other than financial management",
                "71111#Architectural activities",
                "71112#Urban planning and landscape architectural activities",
                "71121#Engineering design activities for industrial process and production",
                "71122#Engineering related scientific and technical consulting activities",
                "71129#Other engineering activities",
                "71200#Technical testing and analysis",
                "72110#Research and experimental development on biotechnology",
                "72190#Other research and experimental development on natural sciences and engineering",
                "72200#Research and experimental development on social sciences and humanities",
                "73110#Advertising agencies",
                "73120#Media representation services",
                "73200#Market research and public opinion polling",
                "74100#specialised design activities",
                "74201#Portrait photographic activities",
                "74202#Other specialist photography",
                "74203#Film processing",
                "74209#Photographic activities not elsewhere classified",
                "74300#Translation and interpretation activities",
                "74901#Environmental consulting activities",
                "74902#Quantity surveying activities",
                "74909#Other professional, scientific and technical activities n.e.c.",
                "74990#Non-trading company",
                "75000#Veterinary activities",


//                "Section N#Administrative and support service activities",
                "77110#Renting and leasing of cars and light motor vehicles",
                "77120#Renting and leasing of trucks and other heavy vehicles",
                "77210#Renting and leasing of recreational and sports goods",
                "77220#Renting of video tapes and disks",
                "77291#Renting and leasing of media entertainment equipment",
                "77299#Renting and leasing of other personal and household goods",
                "77310#Renting and leasing of agricultural machinery and equipment",
                "77320#Renting and leasing of construction and civil engineering machinery and equipment",
                "77330#Renting and leasing of office machinery and equipment (including computers)",
                "77341#Renting and leasing of passenger water transport equipment",
                "77342#Renting and leasing of freight water transport equipment",
                "77351#Renting and leasing of air passenger transport equipment",
                "77352#Renting and leasing of freight air transport equipment",
                "77390#Renting and leasing of other machinery, equipment and tangible goods n.e.c.",
                "77400#Leasing of intellectual property and similar products, except copyright works",
                "78101#Motion picture, television and other theatrical casting activities",
                "78109#Other activities of employment placement agencies",
                "78200#Temporary employment agency activities",
                "78300#Human resources provision and management of human resources functions",
                "79110#Travel agency activities",
                "79120#Tour operator activities",
                "79901#Activities of tourist guides",
                "79909#Other reservation service activities n.e.c.",
                "80100#Private security activities",
                "80200#Security systems service activities",
                "80300#Investigation activities",
                "81100#Combined facilities support activities",
                "81210#General cleaning of buildings",
                "81221#Window cleaning services",
                "81222#Specialised cleaning services",
                "81223#Furnace and chimney cleaning services",
                "81229#Other building and industrial cleaning activities",
                "81291#Disinfecting and exterminating services",
                "81299#Other cleaning services",
                "81300#Landscape service activities",
                "82110#Combined office administrative service activities",
                "82190#Photocopying, document preparation and other specialised office support activities",
                "82200#Activities of call centres",
                "82301#Activities of exhibition and fair organisers",
                "82302#Activities of conference organisers",
                "82911#Activities of collection agencies",
                "82912#Activities of credit bureaus",
                "82920#Packaging activities",
                "82990#Other business support service activities n.e.c.",


//                "Section O#Public administration and defence; compulsory social security",
                "84110#General public administration activities",
                "84120#Regulation of health care, education, cultural and other social services, not incl. social security",
                "84130#Regulation of and contribution to more efficient operation of businesses",
                "84210#Foreign affairs",
                "84220#Defence activities",
                "84230#Justice and judicial activities",
                "84240#Public order and safety activities",
                "84250#Fire service activities",
                "84300#Compulsory social security activities",


//                "Section P#Education",
                "85100#Pre-primary education",
                "85200#Primary education",
                "85310#General secondary education",
                "85320#Technical and vocational secondary education",
                "85410#Post-secondary non-tertiary education",
                "85421#First-degree level higher education",
                "85422#Post-graduate level higher education",
                "85510#Sports and recreation education",
                "85520#Cultural education",
                "85530#Driving school activities",
                "85590#Other education n.e.c.",
                "85600#Educational support services",


//                "Section Q#Human health and social work activities",
                "86101#Hospital activities",
                "86102#Medical nursing home activities",
                "86210#General medical practice activities",
                "86220#Specialists medical practice activities",
                "86230#Dental practice activities",
                "86900#Other human health activities",
                "87100#Residential nursing care facilities",
                "87200#Residential care activities for learning difficulties, mental health and substance abuse",
                "87300#Residential care activities for the elderly and disabled",
                "87900#Other residential care activities n.e.c.",
                "88100#Social work activities without accommodation for the elderly and disabled",
                "88910#Child day-care activities",
                "88990#Other social work activities without accommodation n.e.c.",


//                "Section R#Arts, entertainment and recreation",
                "90010#Performing arts",
                "90020#Support activities to performing arts",
                "90030#Artistic creation",
                "90040#Operation of arts facilities",
                "91011#Library activities",
                "91012#Archives activities",
                "91020#Museums activities",
                "91030#Operation of historical sites and buildings and similar visitor attractions",
                "91040#Botanical and zoological gardens and nature reserves activities",
                "92000#Gambling and betting activities",
                "93110#Operation of sports facilities",
                "93120#Activities of sport clubs",
                "93130#Fitness facilities",
                "93191#Activities of racehorse owners",
                "93199#Other sports activities",
                "93210#Activities of amusement parks and theme parks",
                "93290#Other amusement and recreation activities n.e.c.",


//                "Section S#Other service activities",
                "94110#Activities of business and employers membership organisations",
                "94120#Activities of professional membership organisations",
                "94200#Activities of trade unions",
                "94910#Activities of religious organisations",
                "94920#Activities of political organisations",
                "94990#Activities of other membership organisations n.e.c.",
                "95110#Repair of computers and peripheral equipment",
                "95120#Repair of communication equipment",
                "95210#Repair of consumer electronics",
                "95220#Repair of household appliances and home and garden equipment",
                "95230#Repair of footwear and leather goods",
                "95240#Repair of furniture and home furnishings",
                "95250#Repair of watches, clocks and jewellery",
                "95290#Repair of personal and household goods n.e.c.",
                "96010#Washing and (dry-)cleaning of textile and fur products",
                "96020#Hairdressing and other beauty treatment",
                "96030#Funeral and related activities",
                "96040#Physical well-being activities",
                "96090#Other service activities n.e.c.",


//                "Section T#Activities of households as employers; undifferentiated goods- and services-producing activities of households for own use",
                "97000#Activities of households as employers of domestic personnel",
                "98000#Residents property management",
                "98100#Undifferentiated goods-producing activities of private households for own use",
                "98200#Undifferentiated service-producing activities of private households for own use",


//                "Section U#Activities of extraterritorial organisations and bodies",
                "99000#Activities of extraterritorial organisations and bodies",
                "99999#Dormant Company"
        ));
        RoleDTO r1 = new RoleDTO().builder().roleCode("ANONYMOUS").roleDescription("UYESIZ MUSTERI").build();
        RoleDTO r2 = new RoleDTO().builder().roleCode("CUSTOMER").roleDescription("UYE OLAN MUSTERI").build();
        RoleDTO r3 = new RoleDTO().builder().roleCode("EMPLOYEE").roleDescription("PERSONEL").build();
        RoleDTO r4 = new RoleDTO().builder().roleCode("MANAGER").roleDescription("YÖNETİCİ").build();
        RoleDTO r5 = new RoleDTO().builder().roleCode("ADMIN").roleDescription("SUPER USER").build();
        rolesRepository.saveAll(Arrays.asList(roleMapper.toEntity(r1), roleMapper.toEntity(r2), roleMapper.toEntity(r3), roleMapper.toEntity(r4)));

//        clientTypeRepository.saveAll(Arrays.asList(c1,c2,c3,c4,c5));

        Personel p = new Personel();
        Personel p1 = new Personel();
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode("12345"));
        user1.setName("Gülay");
        user1.setSurname("Aktaş");
        user1.setEmail("gulayhanim1@gmail.com");
        user1.setUserType(UserType.MANAGER);
        user1.setIsDeleted(false);

        user1.setRoles(rolesFromStrings(Arrays.asList("MANAGER")));
        user1.setIsActive(true);
        user1.setLastUpdatedDateTime(LocalDateTime.now());
        user1.setCreatedDateTime(LocalDateTime.now());
        userRepository.save(user1);
        user1.setUserFolder("user\\" + GlobalVariable.converSessiz(user1.getName()) + user1.getId());
        User u1 = userRepository.save(user1);
        p.setUser(u1);
        personelRepository.save(p);

        List<ModuleTypeEnum> moduleTypes = Arrays.asList(ModuleTypeEnum.values());
        String[] module_type_ids = {
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1006')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1007')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1008' WHERE (`id` = '1008')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1009' WHERE (`id` = '1009')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1010' WHERE (`id` = '1010')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1011' WHERE (`id` = '1011')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1012' WHERE (`id` = '1012')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1013' WHERE (`id` = '1013')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1014' WHERE (`id` = '1014')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1015')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1016' WHERE (`id` = '1016')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1017' WHERE (`id` = '1017')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1018' WHERE (`id` = '1018')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1019' WHERE (`id` = '1019')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1020' WHERE (`id` = '1020')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1021' WHERE (`id` = '1021')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1022' WHERE (`id` = '1022')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1023')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1024')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1025')",
                "UPDATE `overcatch`.`module_type` SET `module_type_id` = '1007' WHERE (`id` = '1026')",
               };
        int i = 0;
        for (ModuleTypeEnum a : moduleTypes) {
            ModuleType moduleType = new ModuleType();
            moduleType.setModuleTypeEnum(a);
            moduleType.setName(a.getName());
            moduleTypeRepository.save(moduleType);
            jdbcTemplate.execute(module_type_ids[i]);
            i++;
        }
        String[] letterTypes = {
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (80,'2021-01-18 06:33:28',NULL,'2021-01-18 06:33:28','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiT3VyIFJlZjogIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkZvciBUaGUgQXR0ZW50aW9uIG9mICJ9LHsidGV4dCI6InNlbGVjdGVkRW1iYXNzeU5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiI6In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0Ijoic2VsZWN0ZWRFbWJhc3N5QWRkcmVzcyIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkRlYXIgU2lycywifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJSRTogIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlclN1cm5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgRC5PLkIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyRE9CIiwiY29kZSI6dHJ1ZX1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiV2UgYXJlIHdyaXRpbmcgdG8gY29uZmlybSB0aGF0IGluaXRpYWwgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHJlZ2lzdGVyZWQgYXQgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VySG9tZUFkZHJlc3MiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgaXMgb3VyIGNsaWVudC4gIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIGRpcmVjdG9yIG9mICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc05hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgYW5kIGNvbW1lbmNlZCB0cmFkaW5nIG9uICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc1N0YXJ0RGF0ZSIsImNvZGUiOnRydWV9LHsidGV4dCI6Iiwgd2UgYXNzaXN0ICJ9LHsidGV4dCI6ImhpbWhlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0byBwcmVwYXJlICJ9LHsidGV4dCI6Imhpc2hlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBmaW5hbmNpYWwgYWNjb3VudHMgYW5kIGZpbGUgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHRheCByZXR1cm4gdG8gSE0gUmV2ZW51ZSBhbmQgQ3VzdG9tcyAoSE1SQykuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJGdXJ0aGVyIHdlIGNhbiBjb25maXJtIHRoYXQgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBpcyB1c2luZyBvdXIgYWRkcmVzcyBhcyBhIGNvbXBhbnkgcmVnaXN0ZXJlZCBhZGRyZXNzLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJJbiBvdXIgb3BpbmlvbiwgIn0seyJ0ZXh0IjoiaGVzaGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgaGFzIHRoZSBhYmlsaXR5IHRvIHN1cHBvcnQgdGhlIGNvbW1pdG1lbnRzICJ9LHsidGV4dCI6Imhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIHJlc3BvbnNpYmxlIGZvciBhbmQgYWxzbyBjb25zaWRlciAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdG8gYmUgZ29vZCwgcmVzcGVjdGFibGUgYW5kIHJlYXNvbmFibGUgcGVyc29uIHdobyB3aWxsIGJlIHN1aXRhYmxlIHRvIGNhcnJ5IG91dCB0aGUgb2JsaWdhdGlvbnMgZXhwZWN0ZWQgb2YgIn0seyJ0ZXh0IjoiaGltaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlBsZWFzZSBkbyBub3QgaGVzaXRhdGUgdG8gY29udGFjdCB1cyBpZiB5b3UgbmVlZCBhbnkgZnVydGhlciBpbmZvcm1hdGlvbiBvciBleHBsYW5hdGlvbnMuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJZb3VycyBmYWl0aGZ1bGx5LCJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJSaWdodCBBY2NvdW50aW5nIEx0ZCJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJHdWxheSBBa2JhcyJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19XQ==','Military Service Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (81,'2021-01-18 07:04:05',NULL,'2021-01-18 07:04:05','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlRvIFdob20gSXQgTWF5IENvbmNlcm5cdFx0In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJPdXIgUmVmOiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkRhdGU6ICJ9LHsidGV4dCI6ImRhdGUiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJSRTpcdCJ9LHsidGV4dCI6InNlbGVjdGVkVXNlckhvbWVBZGRyZXNzIiwiY29kZSI6dHJ1ZX1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJPdXIgYWJvdmUtbmFtZWQgY2xpZW50IHdpc2hlcyB0byBpbnZpdGUgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InJlbGF0aW9uIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLCB0byB2aXNpdCAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgaW4gdGhlIFVuaXRlZCBLaW5nZG9tLiAifSx7InRleHQiOiJpbml0aWFsIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlck5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBoYXMgYmVlbiB0aGUgZGlyZWN0b3Igb2YgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiwgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyQnVzc0FkZHJlc3MiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIuICJ9LHsidGV4dCI6ImluaXRpYWwiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBpcyBhIHRydXN0d29ydGh5IHBlcnNvbiBhbmQgaGFzIGhhbmRsZWQgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHRheGF0aW9uIGFmZmFpcnMgaW4gYWNjb3JkYW5jZSB3aXRoIHRoZSBsYXdzIG9mIHRoZSBjb3VudHJ5LiAgUHJlc2VudGx5LCBhbGwgb2YgIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0Ijoi4oCZcyBUYXggYW5kIE5JQyBpcyBmdWxseSBwYWlkIHVwIGFuZCBoYXMgbm8gb3V0c3RhbmRpbmcgZGVidHMgdG8gYW55IG90aGVyIGluc3RpdHV0aW9uLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkhlU2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHdvdWxkIGFsc28gbGlrZSB0byByZS1hc3N1cmUgeW91cnNlbHZlcyB0aGF0IGlmIHBlcm1pc3Npb24gd2VyZSBncmFudGVkIHRvICJ9LHsidGV4dCI6Imhpc2hlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJyZWxhdGlvbiIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0byBlbnRlciB0aGUgY291bnRyeSB0aGVuICJ9LHsidGV4dCI6Imludmhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHdpbGwgYmUgc3RheWluZyB3aXRoICJ9LHsidGV4dCI6ImhpbWhlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBhdCB0aGUgYWJvdmUgYWRkcmVzcyBkdXJpbmcgIn0seyJ0ZXh0IjoiaW52aGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHZpc2l0LiAgTW9yZW92ZXIsICJ9LHsidGV4dCI6Imhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGNhbiBhbHNvIGd1YXJhbnRlZSB0aGF0IGR1cmluZyAifSx7InRleHQiOiJpbnZoaXNoZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdmlzaXQgIn0seyJ0ZXh0IjoiaW52aGVzaGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgd2lsbCBub3QgaGF2ZSBhbnkgcmVjb3Vyc2UgdG8gYW55IHB1YmxpYyBmdW5kcy4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlRoZSBhZm9yZXNhaWQgcGVyc29uIGlzOiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiTkFNRSBBTkQgREFURSBPRiBCSVJUSFx0In0seyJ0ZXh0IjoiaW52aXRlZE5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0IjoiaW52aXRlZFN1cm5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgLSAifSx7InRleHQiOiJpbnZpdGVkRE9CIiwiY29kZSI6dHJ1ZX1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJFTEFUSU9OU0hJUFx0In0seyJ0ZXh0IjoicmVsYXRpb24iLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiQUREUkVTU1x0XHQifSx7InRleHQiOiJpbnZpdGVkQWRkcmVzcyIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6ImluaXRpYWwiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBpcyBhIHdlbGwtcmVzcGVjdGVkIGJ1c2luZXNzcGVyc29uIHdpdGhpbiB0aGUgY29tbXVuaXR5IGFzIHdlbGwgYXMgYSB0cnVzdGVkIHBlcnNvbi4gVGhlcmVmb3JlLCBpdCBpcyBvdXIgb3BpbmlvbiB0aGF0ICJ9LHsidGV4dCI6Imhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGNhbiBiZSB0cnVzdGVkIG9uIHRoZSBtZXJpdHMgb2YgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGNsYWltcy4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkNvdWxkIHlvdSBwbGVhc2UgYXJyYW5nZSBmb3IgIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0Ijoi4oCZcyAifSx7InRleHQiOiJyZWxhdGlvbiIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0byBoYXZlIGVudHJ5IGludG8gdGhlIFVLIHNvIHRoYXQgIn0seyJ0ZXh0IjoiaGVzaGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgbWF5IGJlIGFibGUgdG8gdmlzaXQgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6Im9wb3NpdGVSZWxhdGlvbiIsImNvZGUiOnRydWV9LHsidGV4dCI6Ii4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiS2luZCBSZWdhcmRzIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUmlnaHQgQWNjb3VudGluZyBMdGQifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiR3VsYXkgQWtiYXMifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfV0=','Family Friend Invitation Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (82,'2021-01-18 08:28:21',NULL,'2021-01-18 08:28:21','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiT3VyIFJlZjogIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiVE8gV0hPTSBJVCBNQVkgQ09OQ0VSTiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJcdFx0XHRcdFx0XHRcdFx0XHRcdFx0XHRcdFx0XHRcdFx0XHRcdFx0In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJlOiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlclN1cm5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgRC5PLkIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyRE9CIiwiY29kZSI6dHJ1ZX1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJXZSBhcmUgd3JpdGluZyB0byBjb25maXJtIHRoYXQgIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlclN1cm5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgb2YgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VySG9tZUFkZHJlc3MiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgaXMgb3VyIGNsaWVudC4gIn0seyJ0ZXh0IjoiaW5pdGlhbCIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIG93bmVyIGFuZCBkaXJlY3RvciBvZiAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGFuZCB0aGUgY29tcGFueSBjb21tZW5jZWQgdHJhZGluZyBvbiAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NTdGFydERhdGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgYW5kIHdlIGFzc2lzdCAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdG8gcHJlcGFyZSAifSx7InRleHQiOiJoaXNoZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgZmluYW5jaWFsIGFjY291bnRzIGFuZCBmaWxlICJ9LHsidGV4dCI6Imhpc2hlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0YXggcmV0dXJuIHRvIEhNIFJldmVudWUgYW5kIEN1c3RvbXMgKEhNUkMpLiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkluIG91ciBvcGluaW9uLCAifSx7InRleHQiOiJoZXNoZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBoYXMgdGhlIGFiaWxpdHkgdG8gc3VwcG9ydCB0aGUgY29tbWl0bWVudHMgIn0seyJ0ZXh0IjoiaGVzaGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgaXMgcmVzcG9uc2libGUgZm9yIGFuZCBhbHNvIGNvbnNpZGVyICJ9LHsidGV4dCI6ImhpbWhlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0byBiZSBnb29kLCByZXNwZWN0YWJsZSBhbmQgcmVhc29uYWJsZSBwZXJzb24gd2hvIHdpbGwgYmUgc3VpdGFibGUgdG8gY2Fycnkgb3V0IHRoZSBvYmxpZ2F0aW9ucyBleHBlY3RlZCBvZiAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlBsZWFzZSBkbyBub3QgaGVzaXRhdGUgdG8gY29udGFjdCB1cyBpZiB5b3UgbmVlZCBhbnkgZnVydGhlciBpbmZvcm1hdGlvbiBvciBleHBsYW5hdGlvbnMuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJZb3VycyBzaW5jZXJlbHkifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJpZ2h0IEFjY291bnRpbmcgTHRkIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6Ikd1bGF5IEFrYmFzIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX1d','Accountant Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (83,'2021-01-18 07:27:53',NULL,'2021-01-18 07:27:53','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0Ijoic2VsZWN0ZWRVc2VySG9tZUFkZHJlc3MiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJEZWFyIFNpcnMvIE1hZGFtIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJJIGhhdmUgcmVjZW50bHkgYmVlbiBhcHByb2FjaGVkIGJ5ICJ9LHsidGV4dCI6ImluaXRpYWwiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGRpcmVjdG9yIG9mICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc05hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdG8gYWN0IGFzICJ9LHsidGV4dCI6Imhpc2hlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBhY2NvdW50YW50LiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUGxlYXNlIGNvdWxkIHlvdSBpbmZvcm0gbWUgb2YgYW55IGNpcmN1bXN0YW5jZXMgb3IgaW5mb3JtYXRpb24gd2hpY2ggeW91IGhhdmUga25vd2xlZGdlIG9mLCB3aGljaCBJIG5lZWQgdG8gY29uc2lkZXIgaW4gZGVjaWRpbmcgd2hldGhlciBvciBub3QgdG8gZm9ybWFsbHkgYWNjZXB0IHRoaXMgYXBwb2ludG1lbnQuIElmIHRoZXJlIGFyZSBubyBzdWNoIGNpcmN1bXN0YW5jZXMgdGhhdCBJIG5lZWQgdG8gYmUgYXdhcmUgb2YsIHdvdWxkIHlvdSBwbGVhc2Ugc3VwcGx5IHRoZSBmb2xsb3dpbmcgaW5mb3JtYXRpb246In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIxLlx0QSBub3RlIG9mIHRoZSBhcHByb3ByaWF0ZSB0YXggZGlzdHJpY3QgYW5kIHJlZmVyZW5jZSBudW1iZXJzLCBBdXRoZW50aWNhdGlvbiBDb2RlIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6ImFuZCBDb21wYW55IFJlZ2lzdHJhdGlvbiBudW1iZXIsIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIyLlx0VGhlIGxhc3QgdGF4IHJldHVybiBmb3IgaGVyIGFuZCBoZXIgYnVzaW5lc3NlcyBhbmQgc2NoZWR1bGVzIHN1Ym1pdHRlZCwifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IjMuXHRCdXNpbmVzcyB0YXhhdGlvbiBjb21wdXRhdGlvbnMsIGNhcGl0YWwgYWxsb3dhbmNlIGNsYWltcywgZXRjLCJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiNC5cdENvcGllcyBvZiBhbGwgc2VsZi1hc3Nlc3NtZW50IGFuZCB0YXggcmV0dXJucyByZWxhdGluZyB0byBhbnkgZWFybGllciB0YXggeWVhcnMgd2hpY2ggYXJlIHN0aWxsIG9wZW4gb3Igb24gd2hpY2ggZW5xdWlyaWVzIGFyZSBzdGlsbCBpbiBwcm9ncmVzcy4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IjUuXHRUaGUgbGFzdCBjb21wbGV0ZWQgYWNjb3VudHMgYXZhaWxhYmxlIGZvciB0aGUgYnVzaW5lc3MsIGFsb25nIHdpdGggZGF0ZXMgb2YgYXBwcm92YWwuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiI2Llx0QW55IG90aGVyIGluZm9ybWF0aW9uIG9yIHNjaGVkdWxlcyBldGMuLCB3aGljaCB5b3UgZmVlbCB3aWxsIGJlIG9mIGFzc2lzdGFuY2UuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IjcuXHRPdXRzdGFuZGluZyBDb3JyZXNwb25kZW5jZSB3aXRoIEhNUkMuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiI4Llx0VkFUIFJlZ2lzdHJhdGlvbiBhbmQgVkFUIFJldHVybnMgKGxvZ2luIGRldGFpbHMgaWYgYW55KSJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiI5Llx0QW55IG90aGVyIHJlbGV2YW50IGluZm9ybWF0aW9uIHdoaWNoIG1heSBiZSBoZWxwZnVsIHRvIHVzIGluIHRha2luZyBvdmVyIHRoaXMgY2xpZW50LiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiSWYgeW91IGFyZSBob2xkaW5nIGFueSByZWNvcmRzIGJlbG9uZ2luZyB0byB0aGUgY29tcGFueSwgZm9yIGV4YW1wbGUgVkFUIHJldHVybnMvc3VtbWFyaWVzIGFuZCBwYXlyb2xsIGRvY3VtZW50cywgd2Ugd291bGQgYXBwcmVjaWF0ZSB5b3VyIGVuc3VyaW5nIHRoYXQgdGhleSBhcmUgc2VudCBlaXRoZXIgZGlyZWN0bHkgdG8gdGhlIGNvbXBhbnkgb3IgdG8gb3Vyc2VsdmVzLCBpbiBvcmRlciB0aGF0IHRoZXkgbWF5IGJlIGF2YWlsYWJsZSBpbiB0aGUgZXZlbnQgb2YgYSBmdXR1cmUgSE1SQyBlbnF1aXJ5LiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiV2UgaG9wZSB0byByZWNlaXZlIGFuIGVhcmx5IHJlc3BvbnNlIGFuZCB0aGFuayB5b3UgaW4gYW50aWNpcGF0aW9uIG9mIHlvdXIgYXNzaXN0YW5jZS4gIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiWW91cnMgdHJ1bHkifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJpZ2h0IEFjY291bnRpbmcgTHRkIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6Ikd1bGF5IEFrYmFzIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX1d','Clearance Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (84,'2021-01-18 07:38:16',NULL,'2021-01-18 07:38:16','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0Ijoic2VsZWN0ZWRFbWJhc3N5TmFtZSBpbiBMb25kb24sIFVuaXRlZCBLaW5nZG9tIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6InNlbGVjdGVkRW1iYXNzeUFkZHJlc3MifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6Ik91ciBSZWY6ICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlRPIFdIT00gSVQgTUFZIENPTkNFUk4sIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUkU6IGluaXRpYWwgc2VsZWN0ZWRVc2VyTmFtZSBzZWxlY3RlZFVzZXJTdXJuYW1lIEQuTy5CIHNlbGVjdGVkVXNlckRPQiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJXZSBhcmUgd3JpdGluZyB0byBjb25maXJtIHRoYXQgaW5pdGlhbCBzZWxlY3RlZFVzZXJOYW1lIHNlbGVjdGVkVXNlclN1cm5hbWUgb2Ygc2VsZWN0ZWRVc2VySG9tZUFkZHJlc3MgaXMgb3VyIGNsaWVudC4gaW5pdGlhbCBzZWxlY3RlZFVzZXJOYW1lICwgc2VsZWN0ZWRCdXNzTmFtZSBjb21tZW5jZWQgdHJhZGluZyBvbiBzZWxlY3RlZEJ1c3NTdGFydERhdGUgYW5kIHdlIGFzc2lzdCBoaW1oZXIgdG8gcHJlcGFyZSBoaXNoZXIgZmluYW5jaWFsIGFjY291bnRzIGFuZCBmaWxlIGhpc2hlciB0YXggcmV0dXJuIHRvIEhNIFJldmVudWUgYW5kIEN1c3RvbXMgKEhNUkMpLiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiT3VyIGNsaWVudCBpcyBhIGZpbmFuY2lhbGx5IHNvdW5kIHBlcnNvbiwgd2hvIGlzIGFibGUgdG8gc3VwcG9ydCBhbmQgZnVsbHkgYWNjb21tb2RhdGUgaGltc2VsZmhlcnNlbGYgZHVyaW5nIGhpc2hlciBzdGF5LiBJbiB2aWV3IG9mIHRoZSBhYm92ZSwgd2UgaG9wZSB0aGF0IHlvdSB3aWxsIGdyYW50IGVudHJ5IHZpc2EgZm9yIHRoZSBhYm92ZS4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiSWYgeW91IHJlcXVpcmUgYW55IGZ1cnRoZXIgaW5mb3JtYXRpb24sIHBsZWFzZSBkbyBub3QgaGVzaXRhdGUgdG8gZ2V0IGluIHRvdWNoIHdpdGggdXMuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJZb3VycyBmYWl0aGZ1bGx5In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJHdWxheSBBa2JhcyJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJSaWdodCBBY2NvdW50aW5nIEx0ZCJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19XQ==','Visa Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (85,'2021-01-18 07:50:41',NULL,'2021-01-18 07:50:41','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6ImRhdGUiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6Ik91ciBSZWY6ICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiVE8gV0hPTSBJVCBNQVkgQ09OQ0VSTiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJcdFx0XHRcdFx0XHRcdFx0XHRcdFx0In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJlOiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlclN1cm5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgRC5PLkIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyRE9CIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiV2UgYXJlIHdyaXRpbmcgdG8gY29uZmlybSB0aGF0ICJ9LHsidGV4dCI6ImluaXRpYWwiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIuICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlck5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBvZiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJIb21lQWRkcmVzcyIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBpcyBvdXIgY2xpZW50LiAifSx7InRleHQiOiJpbml0aWFsIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIHRoZSBvd25lciBhbmQgZGlyZWN0b3Igb2YgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB3aGljaCBjb21tZW5jZWQgdHJhZGluZyBvbiAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NTdGFydERhdGUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIsIGFuZCB3ZSBhc3Npc3QgIn0seyJ0ZXh0IjoiaGltaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHRvIHByZXBhcmUgIn0seyJ0ZXh0IjoiaGlzaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGZpbmFuY2lhbCBhY2NvdW50cyBhbmQgZmlsZSAifSx7InRleHQiOiJoaXNoZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdGF4IHJldHVybiB0byBITSBSZXZlbnVlIGFuZCBDdXN0b21zIChITVJDKS4gIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IkluIG91ciBvcGluaW9uLCAifSx7InRleHQiOiJoZXNoZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBoYXMgdGhlIGFiaWxpdHkgdG8gZnVsZmlsIHRoZSBjb21taXRtZW50cyB0aGF0ICJ9LHsidGV4dCI6ImhlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIHJlc3BvbnNpYmxlIGZvciBhbmQgYWxzbyBjb25zaWRlciAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdG8gYmUgYSBnb29kLCByZXNwZWN0YWJsZSBhbmQgcmVhc29uYWJsZSBwZXJzb24gd2hvIHdpbGwgYmUgc3VpdGFibGUgdG8gY2Fycnkgb3V0IHRoZSBvYmxpZ2F0aW9ucyBleHBlY3RlZCBvZiAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJPdXIgY2xpZW50IHJlcXVpcmVzIGEgbmF0aW9uYWwgaW5zdXJhbmNlIG51bWJlciBpbiBvcmRlciB0byByZWdpc3RlciB3aXRoIEhNIFJldmVudWUgQ3VzdG9tcyBhcyBzZWxmLWVtcGxveWVkIHBlcnNvbiBhbmQgVW5pcXVlIFRheCByZWZlcmVuY2UgdG8gYmUgZ2VuZXJhdGVkLiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlBsZWFzZSBmaW5kIGF0dGFjaGVkIGRvY3VtZW50cyByZWxhdGluZyB0byAifSx7InRleHQiOiJoaXNoZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgYnVzaW5lc3MgZm9yIHlvdXIgYXR0ZW50aW9uLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUGxlYXNlIGRvIG5vdCBoZXNpdGF0ZSB0byBjb250YWN0IHVzIGlmIHlvdSBuZWVkIGFueSBmdXJ0aGVyIGluZm9ybWF0aW9uIG9yIGV4cGxhbmF0aW9ucy4gIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJZb3VycyB0cnVseSwifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJpZ2h0IEFjY291bnRpbmcgTHRkIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6Ikd1bGF5IEFrYmFzIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfV0=','NINO Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (86,'2021-01-18 08:03:53',NULL,'2021-01-18 08:03:53','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiVG8gV2hvbSBJdCBNYXkgQ29uY2VybiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJPdXIgUmVmOiAifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJEZWFyIFNpci9NYWRhbSJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJFOiBOYW1lICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlck5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiwgRE9CICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlckRPQiIsImNvZGUiOnRydWV9LHsidGV4dCI6IjogIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJXZSBhcmUgd3JpdGluZyB0byBjb25maXJtIHRoYXQgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLCBkaXJlY3RvciBvZiAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLCAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NBZGRyZXNzIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIG91ciBjbGllbnQuICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc05hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgc3RhcnRlZCB0cmFkaW5nIG9uICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc1N0YXJ0RGF0ZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBhbmQgd2UgYXNzaXN0ICJ9LHsidGV4dCI6ImhpbWhlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB0byBwcmVwYXJlICJ9LHsidGV4dCI6Imhpc2hlciIsImNvZGUiOnRydWV9LHsidGV4dCI6IiBmaW5hbmNpYWwgYWNjb3VudHMgYW5kIGZpbGUgRGlyZWN0b3JzIHRheCByZXR1cm4gdG8gSE0gUmV2ZW51ZSBhbmQgQ3VzdG9tcyAoSE1SQykuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiB5ZWFyIGVuZCBpcyAifSx7InRleHQiOiJzZWxlY3RlZEJ1c3NZZWFyRW5kRGF0ZSIsImNvZGUiOnRydWV9LHsidGV4dCI6Ii4gWWVhciBlbmQgYWNjb3VudHMgYW5kIGZpbmFuY2lhbCBzdGF0ZW1lbnRzIGhhdmUgYmVlbiBjb21wbGV0ZWQuIEZvciB0aGlzIHBlcmlvZCwgdGhlIHR1cm5vdmVyIGlzIMKjICAgICAgICAgICAgICBhbmQgdGhlIG5ldCBwcm9maXQgYmVmb3JlIHRheCBpcyDCoyAgICAgICAgIC4gIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0Ijoi4oCZcyB0b3RhbCBpbmNvbWUgaXMgwqMgICAgICAgICAgICAuIFRvdGFsIEluY29tZSBjb25zaXN0IG9mIERpcmVjdG9yIFNhbGFyeSAmIERpdmlkZW5kLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiSW4gb3VyIG9waW5pb24sICJ9LHsidGV4dCI6Imhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGhhcyB0aGUgYWJpbGl0eSB0byBzdXBwb3J0IHRoZSBjb21taXRtZW50cyB0aGF0ICJ9LHsidGV4dCI6Imhlc2hlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGlzIHJlc3BvbnNpYmxlIGZvciBhbmQgYWxzbyBjb25zaWRlciAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgdG8gYmUgYSBnb29kLCByZXNwZWN0YWJsZSBhbmQgcmVhc29uYWJsZSBwZXJzb24gd2hvIHdpbGwgYmUgc3VpdGFibGUgdG8gY2Fycnkgb3V0IHRoZSBvYmxpZ2F0aW9ucyBleHBlY3RlZCBvZiAifSx7InRleHQiOiJoaW1oZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIuIFdlIGFsc28gY29uc2lkZXIgIn0seyJ0ZXh0IjoiaGltaGVyIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIHRvIGJlIGhvbmVzdCwgdHJ1c3R3b3J0aHkgYW5kIHdpdGggaW50ZWdyaXR5LiBXZSBkbyBub3QgYXdhcmUgb2YgYW55IHJlYXNvbiAifSx7InRleHQiOiJoaXNoZXIiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgYnVzaW5lc3MgbWF5IGNoYW5nZSBpbiB0aGUgZm9yZXNlZWFibGUgZnV0dXJlLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiSW4gYWNjb3JkYW5jZSB3aXRoIG91ciBpbnZhcmlhYmxlIHByYWN0aWNlLCB0aGlzIHJlZmVyZW5jZSBpcyBnaXZlbiBpbiBzdHJpY3QgY29uZmlkZW5jZSBhbmQgd2l0aG91dCBhbnkgb2JsaWdhdGlvbiBvciBsaWFiaWxpdHkgb24gb3VyIHBhcnQuIElmIHlvdSByZXF1aXJlIGFueSBmdXIgdGhpcyBpbmZvcm1hdGlvbiwgcGxlYXNlIGRvIG5vdCBoZXNpdGF0ZSB0byBjb250YWN0IHVzLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJGb3IgZnVydGhlciBpbmZvcm1hdGlvbiBhbmQgYXNzaXN0YW5jZSBwbGVhc2UgZG8gbm90IGhlc2l0YXRlIHRvIGNvbnRhY3QgdXMuICJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiWW91cnMgZmFpdGhmdWxseSwifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUmlnaHQgQWNjb3VudGluZyBMdGQifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiR3VsYXkgQWtiYXMifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfV0=','Rent Reference Letter')",
            "INSERT INTO `overcatch`.`letter_type` (`id`,`insert_time`,`is_active`,`update_time`,`letter_template`,`letter_type_name`) VALUES (87,'2021-01-18 08:11:29',NULL,'2021-01-18 08:11:29','W3sidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiZGF0ZSIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlRvIFdob20gSXQgTWF5IENvbmNlcm46XHRcdFx0XHRcdFx0In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJPdXIgUmVmOiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiRGVhciBTaXIvTWFkYW07In1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlJFOiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJOYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlclN1cm5hbWUiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiRE9COiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJET0IiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiTkk6ICJ9LHsidGV4dCI6InNlbGVjdGVkVXNlck5JTk8iLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiVVRSOiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJVVFIiLCJjb2RlIjp0cnVlfV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiRGlyZWN0b3Igb2Yg4oCYIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IuKAmSJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJzZWxlY3RlZFVzZXJIb21lQWRkcmVzcyIsImNvZGUiOnRydWV9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiUGxlYXNlIGJlIGFkdmlzZWQgdGhhdCBvdXIgY2xpZW50IG5hbWVkIGFib3ZlIGlzIGRpcmVjdG9yIG9mICJ9LHsidGV4dCI6InNlbGVjdGVkQnVzc05hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgc2luY2UgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzU3RhcnREYXRlIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiIGRhdGUuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6InNlbGVjdGVkVXNlck5hbWUiLCJjb2RlIjp0cnVlfSx7InRleHQiOiIgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyU3VybmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiDigJlzIGFjY291bnRzIGFyZSBtYWludGFpbmVkIGF0IG91ciBvZmZpY2UgYW5kIGFueSBzdGF0dXRvcnkgcmV0dXJucy9ub3RpY2VzIHdpbGwgYmUgZnVybmlzaGVkIGFzIGFuZCB3aGVuIHRoZXkgYXJlIGR1ZS4gUGxlYXNlIG5vdGUgdGhhdCAifSx7InRleHQiOiJpbml0aWFsIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0IjoiLiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0Ijoi4oCZcyBUYXggUmV0dXJuIGZvciAyMDE4LzIwMTkgYW5kIE5JTk8gY2FsY3VsYXRpb25zIGFyZSB1cCB0byBkYXRlLiAgIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlwiIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6Ilwi4oCZcyBhY2NvdW50cyBoYXZlIGJlZW4gc2hvcnRlbmVkIGFuZCBwcm92aWRlZCBhdCAzMXN0IE1heSAyMDE5LCBhbHNvIGl0IGlzIGNvbXBsZXRlZCwgYW5kIGFueSBzdGF0dXRvcnkgcmV0dXJucy9ub3RpY2VzIHdpbGwgYmUgZnVybmlzaGVkIGFzIHRoZXkgYXJlIGR1ZS4gRnVydGhlciB3ZSBjYW4gY29uZmlybSB0aGF0IFwiIn0seyJ0ZXh0Ijoic2VsZWN0ZWRCdXNzTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IlwiIGlzIHVzaW5nIG91ciBvZmZpY2UgYWRkcmVzcyBhcyBhIHJlZ2lzdGVyZWQgb2ZmaWNlIGFkZHJlc3MuIn1dfSx7InR5cGUiOiJwYXJhZ3JhcGgiLCJjaGlsZHJlbiI6W3sidGV4dCI6IlllYXIgRW5kIEFjY291bnRzIGhhcyBiZWVuIHByb3ZpZGVkIGF0IFhYWCBZRSBEQVRFIFhYWFguIEZvciB0aGlzIHBlcmlvZCB0aGUgdHVybm92ZXIgaXMgwqNYWFhYWCwgbmV0IHByb2ZpdCBiZWZvcmUgdGF4IGlzIMKjWFhYWCBhbmQgIn0seyJ0ZXh0Ijoic2VsZWN0ZWRVc2VyTmFtZSIsImNvZGUiOnRydWV9LHsidGV4dCI6IiAifSx7InRleHQiOiJzZWxlY3RlZFVzZXJTdXJuYW1lIiwiY29kZSI6dHJ1ZX0seyJ0ZXh0Ijoi4oCZcyBOZXQgTW9udGhseSBEaXJlY3RvciBTYWxhcnkgaXMgwqNYWFhYWC4ifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiSWYgeW91IHJlcXVpcmUgYW55IGZ1cnRoZXIgaW5mb3JtYXRpb24gcGxlYXNlIGRvIG5vdCBoZXNpdGF0ZSB0byB3cml0ZSB0byB1cyBxdW90aW5nIHRoZSBhYm92ZSByZWZlcmVuY2UgbnVtYmVyLiJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19LHsidHlwZSI6InBhcmFncmFwaCIsImNoaWxkcmVuIjpbeyJ0ZXh0IjoiWW91cnMgZmFpdGhmdWxseSJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJSaWdodCBBY2NvdW50aW5nIEx0ZCJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiJHdWxheSBBa2JhcyJ9XX0seyJ0eXBlIjoicGFyYWdyYXBoIiwiY2hpbGRyZW4iOlt7InRleHQiOiIifV19XQ==','Bank Reference Letter')"};
        for (String a : letterTypes) {
            jdbcTemplate.execute(a);
        }
        User user2 = new User();
        user2.setPassword(passwordEncoder.encode("12345"));
        user2.setName("Gülay");
        user2.setSurname("Aktaş");
        user2.setIsDeleted(false);
        user2.setEmail("gulayhanim2@gmail.com");
        user2.setUserType(UserType.EMPLOYEE);
        user2.setRoles(rolesFromStrings(Arrays.asList("EMPLOYEE")));
        user2.setIsActive(true);
        user2.setLastUpdatedDateTime(LocalDateTime.now());
        user2.setCreatedDateTime(LocalDateTime.now());
        userRepository.save(user2);
        user2.setUserFolder("user\\" + GlobalVariable.converSessiz(user2.getName()) + user2.getId());
        User u2 = userRepository.save(user2);
        p1.setUser(u2);
        personelRepository.save(p1);

        User user3 = new User();
        user3.setPassword(passwordEncoder.encode("12345"));
        user3.setName("Gülay");
        user3.setIsDeleted(false);
        user3.setSurname("Aktaş");
        user3.setEmail("gulayhanim3@gmail.com");
        user3.setUserType(UserType.CUSTOMER);
        user3.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));
        user3.setIsActive(true);
        user3.setLastUpdatedDateTime(LocalDateTime.now());
        user3.setCreatedDateTime(LocalDateTime.now());
        userRepository.save(user3);
        user3.setUserFolder("user\\" + GlobalVariable.converSessiz(user3.getName()) + user3.getId());
        userRepository.save(user3);

        User user4 = new User();
        user4.setPassword(passwordEncoder.encode("12345"));
        user4.setName("Mehmet");
        user4.setSurname("Özkan");
        user4.setEmail("mehmett.ozkann@gmail.com");
        user4.setUserType(UserType.CUSTOMER);
        user4.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));
        user4.setIsActive(true);
        user4.setIsDeleted(false);
        user4.setLastUpdatedDateTime(LocalDateTime.now());
        user4.setCreatedDateTime(LocalDateTime.now());
        userRepository.save(user4);
        user4.setUserFolder("user\\" + GlobalVariable.converSessiz(user4.getName()) + user4.getId());
        userRepository.save(user4);
        DirectorDetailDTO directorDetail1 = new DirectorDetailDTO().builder().name("Mehmet").surname("Özkan").visaExpiryDate(LocalDate.now()).nino("Nino123").utr("UTR123").build();
        DirectorDetailDTO directorDetail2 = new DirectorDetailDTO().builder().name("İbrahim").surname("Aydın").visaExpiryDate(LocalDate.now()).nino("Nino453").utr("UTR133").build();
        directorDetailRepository.saveAll(Arrays.asList(directorDetailMapper.toEntity(directorDetail1), directorDetailMapper.toEntity(directorDetail2)));

        ClientDTO a = new ClientDTO();
        a.setClientTypeEnum(ClientTypeEnum.SELFASSESMENT);
        FounderOwnerDTO founderOwnerDTO = new FounderOwnerDTO();
        founderOwnerDTO.setMaritalStatus("Evli");
        a.setFounderOwner(founderOwnerDTO);
        Client c = clientMapper.toEntity(a);
        Client o = clientRepository.save(c);
        DocumentDTO d = new DocumentDTO();
        d.setDocumentName("Ömer");
        Document f = documentRepository.save(documentMapper.toEntity(d));
        f.setDocumentName("Faruk");
        f.setClient(o);
        Document t = documentRepository.save(f);

        HelpType h1 = new HelpType();
        h1.setHelpTypeShowName("Letters");
        HelpType h2 = new HelpType();
        h2.setHelpTypeShowName("Documents");
        HelpType h3 = new HelpType();
        h3.setHelpTypeShowName("Visa");
        HelpType h4 = new HelpType();
        h4.setHelpTypeShowName("Tax");
        HelpType h5 = new HelpType();
        h5.setHelpTypeShowName("VAT");
        HelpType h6 = new HelpType();
        h6.setHelpTypeShowName("Payroll");
        HelpType h7 = new HelpType();
        h7.setHelpTypeShowName("Year End");
        HelpType h8 = new HelpType();
        h8.setHelpTypeShowName("Other");
        HelpType h9 = new HelpType();
        h9.setHelpTypeShowName("Technical Issues");
        helpTypeRepository.save(h9);
        helpTypeRepository.saveAll(Arrays.asList(h1, h2, h3, h4, h5, h6, h7, h8));

        createNatureOfBussinessData();
        /*CompanyDTO c1 = new CompanyDTO().builder().companyName("Solution38 Company").fullName("Solution38 LTI..")
                .birthDay(LocalDate.now()).placeOfBirth("London").phone("+44 7388 799162").ninoNumber("N1212").utr("UTR1213").email("info@solution38.com").pageStatus(PageStatus.CUSTOMER)
                .sessionStatus(SessionStatus.START).companyType(CompanyType.INCORPRATION)
                .note("Content Detail").directorDetails(directorFromStrings(Arrays.asList("Mehmet")))
                .build();
        companyRepository.save(companyMapper.toEntity(c1));
        DirectorDetailDTO directorDetail1 = new DirectorDetailDTO().builder().directorName("Mehmet").visaExpiryDate(LocalDate.now()).nino("Nino123").employeeUTR("UTR123").build();
        DirectorDetailDTO directorDetail2 = new DirectorDetailDTO().builder().directorName("İbrahim").visaExpiryDate(LocalDate.now()).nino("Nino453").employeeUTR("UTR133").build();
        directorDetailRepository.saveAll(Arrays.asList(directorDetailMapper.toEntity(directorDetail1), directorDetailMapper.toEntity(directorDetail2)));

        CompanyDTO c1 = new CompanyDTO().builder().companyName("Solution38 Company").fullName("Solution38 LTI..")
                .birthDay(LocalDate.now()).placeOfBirth("London").phone("+44 7388 799162").ninoNumber("N1212").utr("UTR1213").email("info@solution38.com").pageStatus(PageStatus.CUSTOMER)
                .sessionStatus(SessionStatus.START).companyType(CompanyType.INCORPRATION)
                .note("Content Detail").directorDetails(directorFromStrings(Arrays.asList("Mehmet")))
                .build();

        CompanyDTO c2 = new CompanyDTO().builder().companyName("Tein Company").fullName("Tein AŞ..")
                .birthDay(LocalDate.now()).placeOfBirth("Düzce").phone("+44 7388 799162").ninoNumber("N1242").utr("UTR1213").email("info@tein.com").pageStatus(PageStatus.CUSTOMER)
                .sessionStatus(SessionStatus.START).companyType(CompanyType.INCORPRATION)
                .note("Content Detail").directorDetails(directorFromStrings(Arrays.asList("İbrahim")))
                .build();


        CompanyDTO c3 = new CompanyDTO().builder().companyName("Solution44 Company").fullName("Solution48 LTI..")
                .birthDay(LocalDate.now()).placeOfBirth("London").phone("+44 7388 799162").ninoNumber("N1276").utr("UTR1753").email("info@solution44.com").pageStatus(PageStatus.CUSTOMER)
                .sessionStatus(SessionStatus.START).companyType(CompanyType.SOLETRADER)
                .note("Content Detail").directorDetails(directorFromStrings(Arrays.asList("İbrahim","Mehmet")))
                .build();

        companyRepository.saveAll(Arrays.asList(companyMapper.toEntity(c1), companyMapper.toEntity(c2), companyMapper.toEntity(c3)));

        IncorprationCompanyDTO ic1 = new IncorprationCompanyDTO().builder().yearEndDate(LocalDate.now()).dueDate(LocalDate.now())
                .registration("Registration").authentication("Authentication").paye("Paye").companyUtr("CompanyUtr")
                .nationality("TR").natureBusinesses(natureBusinesFromStrings(Arrays.asList("01110", "01500", "02300")))
                .companyInfo(c1).build();

        incorprationCompanyRepository.saveAll(Arrays.asList(incorprationCompanyMapper.toEntity(ic1)));

        SoleTradeCompanyDTO st1 = new SoleTradeCompanyDTO().builder().visaDateIssue(LocalDate.now())
                .visaExpiryDate(LocalDate.now()).maritalStatus("1")
                .numberOfDependence("5").companyInfo(c2).build();

        SoleTradeCompanyDTO st2 = new SoleTradeCompanyDTO().builder().visaDateIssue(LocalDate.now())
                .visaExpiryDate(LocalDate.now()).maritalStatus("0")
                .numberOfDependence("4").companyInfo(c3).build();

        soleTradeCompanyRepository.saveAll(Arrays.asList(soleTradeCompanyMapper.toEntity(st1),soleTradeCompanyMapper.toEntity(st2)));

        AddressDTO a1= new AddressDTO().builder()
                .city("London")
                .addressType(AddressType.HOME)
                .street("1011")
                .district("Abbey Wood")
                .neighbourhood("Neighbourhood 1")
                .number(103)
                .postcode("34794")
                .company(c1)
                .build();
        AddressDTO a2= new AddressDTO().builder()
                .city("London")
                .addressType(AddressType.BUSINESS)
                .street("1011")
                .district("Abbey Wood")
                .neighbourhood("Neighbourhood 1")
                .number(103)
                .postcode("34794")
                .company(c1)
                .build();
        AddressDTO a3= new AddressDTO().builder()
                .city("Oxford")
                .addressType(AddressType.HOME)
                .street("120")
                .district("Radley")
                .neighbourhood("Neighbourhood 2")
                .postcode("34125")
                .company(c2)
                .number(100)
                .build();
        AddressDTO a4= new AddressDTO().builder()
                .city("Liverpool")
                .addressType(AddressType.BUSINESS)
                .street("130")
                .district("Everton")
                .neighbourhood("Neighbourhood 3")
                .postcode("87175")
                .company(c2)
                .number(255)
                .build();
        addressRepository.saveAll(addressMapper.toEntity(Arrays.asList(a1,a2,a3,a4)));

        UserDTO u1 = new UserDTO().builder().username("mehmet.ozkan").password("over123").brpNumber("V2T4452").brpExpireDate(LocalDate.now())
                .name("Mehmet").surname("Özkan").email("mehmett.ozkann@gmail.com").msisdn("05377837905").userType(UserType.CUSTOMER)
                .roles(rolesFromStrings(Arrays.asList("ANONYMOUS", "MEMBER", "EMPLOYEE", "MANAGER"))).build();
        User user1 = userMapper.toEntity(u1);
        user1.setIsActive(true);
        user1.setLastUpdatedDateTime(LocalDateTime.now());
        user1.setCreatedDateTime(LocalDateTime.now());


        UserDTO u2 = new UserDTO().builder().username("ibrahim.aydin").password("over123").brpNumber("V4T4322").brpExpireDate(LocalDate.now())
                .name("ibrahim").surname("Özkan").email("ibrahim.aydin@gmail.com").msisdn("05302405065").userType(UserType.CUSTOMER)
                .roles(rolesFromStrings(Arrays.asList("ANONYMOUS", "MANAGER"))).build();

        User user2= userMapper.toEntity(u2);
        user2.setIsActive(true);
        user2.setLastUpdatedDateTime(LocalDateTime.now());
        user2.setCreatedDateTime(LocalDateTime.now());

        userRepository.saveAll(Arrays.asList(user1, user2));

        UserCompanyDTO uc1 = new UserCompanyDTO().builder().company(c1).sharePercent(100).user(u1).build();
        UserCompanyDTO uc2 = new UserCompanyDTO().builder().company(c2).sharePercent(40).user(u1).build();
        UserCompanyDTO uc3 = new UserCompanyDTO().builder().company(c2).sharePercent(60).user(u2).build();
        UserCompanyDTO uc4 = new UserCompanyDTO().builder().company(c3).sharePercent(100).user(u2).build();

        usersCompanyRepository.saveAll(Arrays.asList(userCompanyMapper.toEntity(uc1), userCompanyMapper.toEntity(uc2), userCompanyMapper.toEntity(uc3), userCompanyMapper.toEntity(uc4)));
*/
    }

    public Set<Roles> rolesFromStrings(List<String> roles) {
        Set<Roles> rolesSet = new HashSet<>();
        roles.forEach((String item) -> {
            Roles byRoleCode = rolesRepository.findByRoleCode(item).orElseThrow(() -> new AppException("User Role not set."));
            Roles role = null;
            if (byRoleCode != null) {
                role = byRoleCode;
            } else {
                new Exception("Role not found");
            }
            rolesSet.add(role);
        });
        return rolesSet;
    }

    public Set<DirectorDetailDTO> directorFromStrings(List<String> direktor) {
        Set<DirectorDetailDTO> directorSet = new HashSet<>();
        direktor.forEach((String item) -> {
            DirectorDetailDTO byDirectorName = directorDetailMapper.toDto(directorDetailRepository.findByName(item));
            DirectorDetailDTO director = null;
            if (byDirectorName != null) {
                director = byDirectorName;
            } else {
                new Exception("Director not found");
            }
            directorSet.add(director);
        });
        return directorSet;
    }

    public void createNatureOfBussinessData() {
        for (String s : natureBusiness) {
            NatureBusiness n1 = new NatureBusiness();
            n1.setCode(s.split("#")[0]);
            n1.setDescription(s.split("#")[1]);
            natureBusinessRepository.save(n1);
        }
    }

    public Set<NatureBusinessDTO> natureBusinesFromStrings(List<String> nature) {
        Set<NatureBusinessDTO> natureList = new HashSet<>();

        //  List<String> collects = nature.stream().map(s ->  s.split(",")).flatMap(Arrays::stream).collect(Collectors.toList());

        nature.forEach((String item) -> {
            NatureBusinessDTO bynatureBusinessCode = natureBusinessMapper.toDto(natureBusinessRepository.findByCode(item));
            NatureBusinessDTO code = null;
            if (bynatureBusinessCode != null) {
                code = bynatureBusinessCode;
            } else {
                new Exception("Code not found");
            }
            natureList.add(code);
        });
        return natureList;
    }

}
