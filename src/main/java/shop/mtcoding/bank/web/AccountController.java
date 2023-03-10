package shop.mtcoding.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountTransferReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountWithdrawReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountDepositRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountDetailRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountListRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountTransferRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountWithdrawRespDto;
import shop.mtcoding.bank.service.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveReqDto accountSaveReqDto,
            BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        AccountSaveRespDto accountSaveRespDto = accountService.????????????(accountSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", accountSaveRespDto), HttpStatus.CREATED);
    }

    // ????????? ????????????, account ???????????? 1??? row??? ?????????!!
    // cos??? ???????????? ?????????, cos??? id??? 2????????????!!
    // ???????????? ????????? ???????????? ?????????.

    // ????????? ????????????, account ????????? ????????? ??? ?????????!!

    // ????????? ????????????, account ???????????? login??? ????????? ????????? ?????????.
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser) {
        AccountListRespDto accountListRespDto = accountService.??????????????????_?????????(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "??????????????????_????????? ??????", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/s/account/{number}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long number, @AuthenticationPrincipal LoginUser loginUser) {
        accountService.????????????(number, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", null), HttpStatus.OK);
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountDepositReqDto accountDepositReqDto,
            BindingResult bindingResult) {
        AccountDepositRespDto accountDepositRespDto = accountService.????????????(accountDepositReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", accountDepositRespDto), HttpStatus.CREATED);
    }

    @PostMapping("/s/account/withdraw")
    public ResponseEntity<?> withdrawAccount(@RequestBody @Valid AccountWithdrawReqDto accountWithdrawReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser) {
        AccountWithdrawRespDto accountWithdrawRespDto = accountService.????????????(accountWithdrawReqDto,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", accountWithdrawRespDto), HttpStatus.CREATED);
    }

    @PostMapping("/s/account/transfer")
    public ResponseEntity<?> transferAccount(@RequestBody @Valid AccountTransferReqDto accountTransferReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser) {
        AccountTransferRespDto accountTransferRespDto = accountService.????????????(accountTransferReqDto,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", accountTransferRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/s/account/{number}")
    public ResponseEntity<?> findDetailAccount(@PathVariable Long number,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @AuthenticationPrincipal LoginUser loginUser) {
        AccountDetailRespDto accountDetailRespDto = accountService.??????????????????(number, loginUser.getUser().getId(),
                page);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????????????????? ??????", accountDetailRespDto), HttpStatus.OK);
    }
}
