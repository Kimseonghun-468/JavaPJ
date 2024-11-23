package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.FriendAcceptRepository;
import com.skhkim.instaclone.repository.FriendWaitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Log4j2
public class FriendShipServiceImpl implements FriendShipService{

    private final ClubMemberRepository memberRepository;
    private final FriendWaitRepository waitRepository;
    private final FriendAcceptRepository acceptRepository;
    @Override
    @Transactional
    public boolean createFriendShip(String userName){
        String loginName = LoginContext.getUserInfo().getUserName();

        // Select Member Record By Name (Login, Search User Name)
        ClubMember loginMember = memberRepository.findByName(loginName);
        ClubMember userMember = memberRepository.findByName(userName);

        FriendWait friendWait = FriendWait.builder()
                .requester(loginMember)
                .receiver(userMember)
                .build();

        if(checkDuplication(loginName, userName)) {
            waitRepository.save(friendWait);
            return true;
        }
        else return false;

    }

    @Override
    /* FriendWait Duplication Check */
    public boolean checkDuplication(String loginName, String userName){
        Optional<FriendWait> resultWait = memberRepository.getWaitByName(loginName, userName);
        Optional<FriendAccept> resultAccept = memberRepository.getAcceptFriend(loginName, userName);
        return (resultWait.isEmpty() && resultAccept.isEmpty());
    }
    @Override
    public FriendStatus checkFriendShip(String loginName, String userName) {

        if (loginName.equals(userName))
            return FriendStatus.SELF;

        Optional<FriendWait> friendWait = memberRepository.getWaitByName(loginName, userName);
        Optional<FriendAccept> friendAccept = memberRepository.getAcceptFriend(loginName, userName);

        if(friendWait.isPresent()){
            if (friendWait.get().getRequester().getName().equals(loginName))
                return FriendStatus.REQUESTER;
            else if(friendWait.get().getReceiver().getName().equals(loginName))
                return FriendStatus.RECEIVER;
        }

        if (friendAccept.isPresent())
            return FriendStatus.ACCEPTED;

        return FriendStatus.NONE;
    }

    @Override
    @Transactional
    public boolean acceptFriendShip(String userName){

        String loginName = LoginContext.getUserInfo().getUserName();

        Optional<FriendWait> friendWait = memberRepository.getWaitByName(loginName, userName);

        if(friendWait.isPresent()){
            int waitCount = waitRepository.delete(loginName, userName);

            if (waitCount == 1) {
                FriendAccept friendAccept = FriendAccept.builder()
                        .user1(friendWait.get().getReceiver())
                        .user2(friendWait.get().getRequester())
                        .build();
                acceptRepository.save(friendAccept);
                // Wait Record 조회 - 삭제 - accept Record 삽입시 성공
                return true;
            }
            // Delete Count != 1인 경우 Error
            else return false;
        }
        // Wait Table에 Record가 없는 경우 Error
        else return false;
    }

    @Override
    @Transactional
    public boolean deleteFriendShip(String userName){
        String loginName = LoginContext.getUserInfo().getUserName();
        int result = acceptRepository.delete(loginName, userName);
        return result > 0;
    }

    @Override
    public int getFriendNum(String userName){
        return acceptRepository.getCount(userName);

    }

}
