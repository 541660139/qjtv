package com.lwd.qjtv.mvp.model.api.service;

import com.lwd.qjtv.app.utils.Constant;
import com.lwd.qjtv.mvp.model.entity.AddAddressBean;
import com.lwd.qjtv.mvp.model.entity.AddCollectionBean;
import com.lwd.qjtv.mvp.model.entity.AddressBean;
import com.lwd.qjtv.mvp.model.entity.AliH5PayBean;
import com.lwd.qjtv.mvp.model.entity.AliPayBean;
import com.lwd.qjtv.mvp.model.entity.AwardBean;
import com.lwd.qjtv.mvp.model.entity.BBSDataBean;
import com.lwd.qjtv.mvp.model.entity.BBSMessageListBean;
import com.lwd.qjtv.mvp.model.entity.BBSNewsListBean;
import com.lwd.qjtv.mvp.model.entity.BannerBean;
import com.lwd.qjtv.mvp.model.entity.BaseBean;
import com.lwd.qjtv.mvp.model.entity.ChampionBetBean;
import com.lwd.qjtv.mvp.model.entity.ChampionGuess;
import com.lwd.qjtv.mvp.model.entity.ChangeNameBean;
import com.lwd.qjtv.mvp.model.entity.CheckVersionBean;
import com.lwd.qjtv.mvp.model.entity.ChoiceDataBean;
import com.lwd.qjtv.mvp.model.entity.CommentMessageBean;
import com.lwd.qjtv.mvp.model.entity.CommentResultBean;
import com.lwd.qjtv.mvp.model.entity.CommunityAllBean;
import com.lwd.qjtv.mvp.model.entity.CommunityDataBean;
import com.lwd.qjtv.mvp.model.entity.CommunityHfBean;
import com.lwd.qjtv.mvp.model.entity.DetailsMoneyBean;
import com.lwd.qjtv.mvp.model.entity.DianZanBean;
import com.lwd.qjtv.mvp.model.entity.FansOrFollerBean;
import com.lwd.qjtv.mvp.model.entity.FengJinBean;
import com.lwd.qjtv.mvp.model.entity.GameDataBean;
import com.lwd.qjtv.mvp.model.entity.GameTabBean;
import com.lwd.qjtv.mvp.model.entity.GetLevelBean;
import com.lwd.qjtv.mvp.model.entity.GetToken;
import com.lwd.qjtv.mvp.model.entity.GuessCenterBean;
import com.lwd.qjtv.mvp.model.entity.GuessChampionBean;
import com.lwd.qjtv.mvp.model.entity.GuessDetailsBean;
import com.lwd.qjtv.mvp.model.entity.GuessMoreBean;
import com.lwd.qjtv.mvp.model.entity.GuessMyBean;
import com.lwd.qjtv.mvp.model.entity.GuessRankBean;
import com.lwd.qjtv.mvp.model.entity.HotPointBean;
import com.lwd.qjtv.mvp.model.entity.HotSearchCollectionBean;
import com.lwd.qjtv.mvp.model.entity.LearnBallBean;
import com.lwd.qjtv.mvp.model.entity.LearnBallDetailsBean;
import com.lwd.qjtv.mvp.model.entity.LikeAvaterListBean;
import com.lwd.qjtv.mvp.model.entity.LoginBean;
import com.lwd.qjtv.mvp.model.entity.LuckyListBean;
import com.lwd.qjtv.mvp.model.entity.MallListBean;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionDetailsBean;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionMoreBean;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionNewListBean;
import com.lwd.qjtv.mvp.model.entity.MatchCollectionTitleBean;
import com.lwd.qjtv.mvp.model.entity.MatchTimeBean;
import com.lwd.qjtv.mvp.model.entity.MatchTimeList;
import com.lwd.qjtv.mvp.model.entity.MoreTeachListBean;
import com.lwd.qjtv.mvp.model.entity.MoreVideoBean;
import com.lwd.qjtv.mvp.model.entity.MyCommentListBean;
import com.lwd.qjtv.mvp.model.entity.MyCommunityListDataBean;
import com.lwd.qjtv.mvp.model.entity.MyLikeListBean;
import com.lwd.qjtv.mvp.model.entity.MyOrderBean;
import com.lwd.qjtv.mvp.model.entity.OrderExpressBean;
import com.lwd.qjtv.mvp.model.entity.ParseModule;
import com.lwd.qjtv.mvp.model.entity.PersonalWareHouseBean;
import com.lwd.qjtv.mvp.model.entity.PingFenBean;
import com.lwd.qjtv.mvp.model.entity.PublisDataBean;
import com.lwd.qjtv.mvp.model.entity.RegisterBean;
import com.lwd.qjtv.mvp.model.entity.RelatedVideoBean;
import com.lwd.qjtv.mvp.model.entity.SearchBean;
import com.lwd.qjtv.mvp.model.entity.SearchCollectionBean;
import com.lwd.qjtv.mvp.model.entity.SearchMallBean;
import com.lwd.qjtv.mvp.model.entity.SendGiftBean;
import com.lwd.qjtv.mvp.model.entity.ShareBean;
import com.lwd.qjtv.mvp.model.entity.StarCommentBean;
import com.lwd.qjtv.mvp.model.entity.StarDetailsBean;
import com.lwd.qjtv.mvp.model.entity.SwiftPassPayBean;
import com.lwd.qjtv.mvp.model.entity.UploadPicBean;
import com.lwd.qjtv.mvp.model.entity.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import com.lwd.qjtv.mvp.model.entity.UserInfro;
import com.lwd.qjtv.mvp.model.entity.VideoCollectionBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsBean;
import com.lwd.qjtv.mvp.model.entity.VideoDetailsNewBean;
import com.lwd.qjtv.mvp.model.entity.WatchBallBean;
import com.lwd.qjtv.mvp.model.entity.WechatH5PayBean;
import com.lwd.qjtv.mvp.model.entity.WechatPayBean;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 存放关于用户的一些api
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface UserService {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";


    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);

    @GET(Constant.HTTP＿HEAD + Constant.SEND_MSG)
    Observable<BaseBean> sendMsg(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.REGISTER)
    Observable<RegisterBean> register(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LOGIN)
    Observable<LoginBean> login(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BET_CENNTER)
    Observable<BaseBean> addMoreBet(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GET_TOKEN)
    Observable<GetToken> getToken(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_CENTER)
    Observable<GuessDetailsBean> getGuessDetails(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.NBA_GUESS_CENTER)
    Observable<GuessDetailsBean> getNBAGuessDetails(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.CHECK_VERSION)
    Observable<CheckVersionBean> checkVersion(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_RANK)
    Observable<GuessRankBean> guessRank(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.NBA_RANK_LIST)
    Observable<GuessRankBean> guessNBARank(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.RECHARGE)
    Observable<GuessRankBean> getRechargeList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MY_ORDER)
    Observable<OrderExpressBean> getOrderExpress(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADDRESS_LIST)
    Observable<BaseBean> deleteAddress(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.DETAILS_MONEY)
    Observable<DetailsMoneyBean> getDetailsMoney(@QueryMap Map<String, String> map);

    @Multipart
    @POST("upload.php?tp=" + Constant.UP_PIC)
    Observable<UploadPicBean> uploadPic(@Part MultipartBody.Part requestBody, @QueryMap Map<String, String> map);

    @Multipart
    @POST("upload.php?tp=" + Constant.UP_PIC_SLK)
    Observable<UploadPicBean> uploadPicSlk(@Part MultipartBody.Part requestBody, @QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GET_USER_INFO)
    Observable<GetLevelBean> getUserInfo(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.USER_FEEDBACK)
    Observable<BaseBean> feedBack(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GIFT_RELATE)
    Observable<PersonalWareHouseBean> getPersonalWarehouse(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GIFT_RELATE)
    Observable<BaseBean> exchangeGift(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GIFT_RELATE)
    Observable<SendGiftBean> sendGift(@QueryMap Map<String, String> map);

    //    看球
    @GET(Constant.HTTP＿HEAD + Constant.HOME_INDEX)
    Observable<WatchBallBean> getWatchBall(@QueryMap Map<String, String> map);

    //获取热点
    @GET(Constant.HTTP＿HEAD + Constant.HOME_INDEX)
    Observable<HotPointBean> getHotPoint(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.MATCH_TIME)
    Observable<MatchTimeBean> getMatchTime(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_COLLECTION_LIST)
    Observable<MatchCollectionNewListBean> getMatchCollectionList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_TIME)
    Observable<MatchTimeList> getMatchTimeList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LEARN_LIST)
    Observable<LearnBallBean> getLearnBall(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LEARN_BALL_DETAILS)
    Observable<LearnBallDetailsBean> getLearnBallDetails(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MALL)
    Observable<MallListBean> getMall(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ORDER)
    Observable<BaseBean> orderLive(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.VIDEO_DETAILS)
    Observable<VideoDetailsBean> getVideoDetails(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.VIDEO_DETAILS_NEW)
    Observable<VideoDetailsNewBean> getVideoDetailsNew(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.SEARCH)
    Observable<SearchBean> getSearch(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MALL_SEARCH)
    Observable<SearchMallBean> getSearchMall(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADDRESS_LIST)
    Observable<AddressBean> getAddressList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADDRESS_LIST)
    Observable<AddAddressBean> addAddress(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_CENTER)
    Observable<GuessCenterBean> getGuessCenter(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.NBA_GUESS_CENTER)
    Observable<GuessCenterBean> getNBAGuessCenter(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.SHARE)
    Observable<ShareBean> getShareInfo(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MINE_GUESS)
    Observable<GuessMyBean> getGuessMy(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MINE_GUESS)
    Observable<ChampionGuess> getChampionGuess(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MINE_GUESS)
    Observable<GuessMoreBean> getGuessMore(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LUCKY_PAN)
    Observable<LuckyListBean> getLuckyList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LUCKY_PAN)
    Observable<AwardBean> getAward(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.WECHAT_PAY)
    Observable<WechatPayBean> wechatPay(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.WECHAT_H5_PAY)
    Observable<WechatH5PayBean> wechatH5Pay(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ALI_PAY_TP)
    Observable<AliPayBean> aliPay(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ALI_PAY_H5_TP)
    Observable<AliH5PayBean> aliH5Pay(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_CHAMPION)
    Observable<GuessChampionBean> getGuessChampion(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_CHAMPION)
    Observable<ChampionBetBean> addChampionBet(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.COMMENT_LIST)
    Observable<CommentMessageBean> getCommentMessage(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.CHANGE_NAME)
    Observable<ChangeNameBean> changeName(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADD_COMMENTS)
    Observable<BaseBean> addCommentMessage(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.COMMENT_LIKE)
    Observable<BaseBean> likeComment(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.SWIFTPASS_PAY)
    Observable<SwiftPassPayBean> getSwiftPassPay(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.VIDEO_COLLECTION)
    Observable<VideoCollectionBean> getVideoCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADD_VIDEO_COLLECTION)
    Observable<AddCollectionBean> addVideoCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.CANCEL_VIDEO_COLLECTION)
    Observable<BaseBean> deleteVideoCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.STAR_DETAILS)
    Observable<StarDetailsBean> getStarDetails(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADD_STAR_COMMENT)
    Observable<BaseBean> addStarComment(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.STAR_COMMENT_LIST)
    Observable<StarCommentBean> getStarCommentList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LIKE_STAR_COMMENT)
    Observable<BaseBean> likeStarComment(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.RELATED_VIDEO)
    Observable<RelatedVideoBean> getRelatedVideoList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MORE_VIDEO)
    Observable<MoreVideoBean> getMoreVideoList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MORE_TEACH_LIST_VIDEO)
    Observable<MoreTeachListBean> getMoreTeachListVideo(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.GUESS_CENTER)
    Observable<BaseBean> addGuessBat(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.NBA_GUESS_CENTER)
    Observable<BaseBean> addNBAGuessBat(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MY_ORDER)
    Observable<MyOrderBean> getMyOrder(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.FENGJIN_USER)
    Observable<FengJinBean> fengjinUser(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.ADD_PINGFEN)
    Observable<PingFenBean> addPingFen(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.LIVE_USER)
    Observable<BaseBean> liveUser(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MORE_VIDEO)
    Observable<MatchCollectionMoreBean> getMatchCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_COLLECTION_DETAIL)
    Observable<MatchCollectionDetailsBean> getMatchCollectionDetails(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_COLLECTION_DETAIL)
    Observable<MatchCollectionTitleBean> getMatchCollectionTitle(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_COLLECTION_SEARCH)
    Observable<HotSearchCollectionBean> getHotSearchMatchCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.MATCH_COLLECTION_SEARCH)
    Observable<SearchCollectionBean> searchMatchCollection(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MAIN)
    Observable<BBSDataBean> getBBSData(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MAIN_PUSH_MSG)
    Observable<BaseBean> pushCommunity(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MAIN_PUSH_MSG)
    Observable<MyCommunityListDataBean> getMyBBSData(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MSG_LIST)
    Observable<BBSMessageListBean> getMsgList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MSG_LIST)
    Observable<MyCommentListBean> getCommentList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MSG_LIST)
    Observable<MyLikeListBean> getLikeList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_NEWS)
    Observable<BBSNewsListBean> getBBSNews(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_MSG_LIST)
    Observable<LikeAvaterListBean> getLikeAvaterList(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.BBS_COMMENT)
    Observable<CommentResultBean> commentBBSDetails(@QueryMap Map<String, String> map);

    @GET
    Observable<ParseModule> getParseFile(@Url String url);

    @GET
    Observable<Response<ResponseBody>> downloadParseFile(@Url String url);


    /**
     * 支付回调
     */
    @GET(Constant.HTTP＿HEAD + Constant.PAY_CALLBACK)
    Observable<BaseBean> GetPayMsg(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.CHOICE_DATA)
    Observable<ChoiceDataBean> getChoiceData(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.CHOICE_DATA_DIAN_ZAN)
    Observable<DianZanBean> setDianZan(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.GET_BANNER)
    Observable<BannerBean> getBanner(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.VIDEO_DETAILS_NEW_PL)
    Observable<BaseBean> setLikeComment(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.COMMUNITY)
    Observable<CommunityDataBean> getCommunity(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.FOLLOW)
    Observable<BaseBean> setFollow(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.PERSONAL_CENTER)
    Observable<UserInfro> getPersonalCenter(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.PERSONAL_CENTER)
    Observable<FansOrFollerBean> getPersonalALl(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.PERSONAL_INDEX)
    Observable<PublisDataBean> getPersonalIndex(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.PERSONAL_CENTER)
    Observable<PublisDataBean> getMeIndex(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.DELETER_PUBLIS)
    Observable<BaseBean> deleterPublis(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.MY_COLLECT)
    Observable<BaseBean> setCollect(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.GAME_TAB)
    Observable<GameTabBean> getGameTab(@QueryMap Map<String, String> map);



    @GET(Constant.HTTP＿HEAD + Constant.GAME_TAB)
    Observable<GameDataBean> getGameData(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.getCommunityDetail)
    Observable<CommunityAllBean> getCommunityDetail(@QueryMap Map<String, String> map);


    @GET(Constant.HTTP＿HEAD + Constant.SEND_COMMUNITY_CONTENT)
    Observable<BaseBean> sendCommunitycontent(@QueryMap Map<String, String> map);

    @GET(Constant.HTTP＿HEAD + Constant.SEND_COMMUNITY_CONTENT)
    Observable<CommunityHfBean> sendCommunityHfcontent(@QueryMap Map<String, String> map);


}
