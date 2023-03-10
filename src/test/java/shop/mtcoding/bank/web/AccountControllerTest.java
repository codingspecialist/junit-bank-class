package shop.mtcoding.bank.web;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountDepositReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountTransferReqDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountWithdrawReqDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class AccountControllerTest extends DummyObject {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper om;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private EntityManager em;

        @BeforeEach
        public void setUp() {
                dataSetting();
                em.clear();
        }

        // jwt token -> ???????????? -> ???????????? ????????????
        // setupBefore=TEST_METHOD (setUp ????????? ???????????? ??????)
        // setupBefore=TEST_EXECUTION (saveAccount_test ????????? ???????????? ??????)
        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // ???????????? username=ssar ????????? ??????
                                                                                          // ?????????
                                                                                          // ???????????? ???????????????!!
        @Test
        public void saveAccount_test() throws Exception {
                // given
                AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
                accountSaveReqDto.setNumber(9999L);
                accountSaveReqDto.setPassword(1234L);
                String requestBody = om.writeValueAsString(accountSaveReqDto);
                System.out.println("????????? : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/s/account").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findUserAccount_test() throws Exception {
                // given

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/s/account/login-user"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        /*
         * ?????????????????? insert ???????????? ?????? PC??? ????????? (?????????)
         * ????????? ???????????? ????????? ????????? ?????? ?????? ????????? ????????? ???????????? ???????????? ??? ??? ?????? ?????????.
         * ?????? select??? ????????? ???????????????!! - PC??? ????????? 1??? ????????? ???.
         * Lazy ????????? ????????? ???????????? - PC??? ?????????!!
         * Lazy ????????? ??? PC ????????? ????????? ?????????.
         */
        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void deleteAccount_test() throws Exception {
                // given
                Long number = 1111L;

                // when
                ResultActions resultActions = mvc
                                .perform(delete("/api/s/account/" + number));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                // Junit ??????????????? delete ?????? ????????? DB??????(DML)?????? ?????? ???????????? ???????????? ????????????.
                assertThrows(CustomApiException.class, () -> accountRepository.findByNumber(number).orElseThrow(
                                () -> new CustomApiException("????????? ?????? ??? ????????????")));

        }

        @Test
        public void depositAccount_test() throws Exception {
                // given
                AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
                accountDepositReqDto.setNumber(1111L);
                accountDepositReqDto.setAmount(100L);
                accountDepositReqDto.setGubun("DEPOSIT");
                accountDepositReqDto.setTel("01088887777");

                String requestBody = om.writeValueAsString(accountDepositReqDto);
                System.out.println("????????? : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/account/deposit").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void withdrawAccount_test() throws Exception {
                // given
                AccountWithdrawReqDto accountWithdrawReqDto = new AccountWithdrawReqDto();
                accountWithdrawReqDto.setNumber(1111L);
                accountWithdrawReqDto.setPassword(1234L);
                accountWithdrawReqDto.setAmount(100L);
                accountWithdrawReqDto.setGubun("WITHDRAW");

                String requestBody = om.writeValueAsString(accountWithdrawReqDto);
                System.out.println("????????? : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/s/account/withdraw").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? ?????? : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void transferAccount_test() throws Exception {
                // given
                AccountTransferReqDto accountTransferReqDto = new AccountTransferReqDto();
                accountTransferReqDto.setWithdrawNumber(1111L);
                accountTransferReqDto.setDepositNumber(2222L);
                accountTransferReqDto.setWithdrawPassword(1234L);
                accountTransferReqDto.setAmount(100L);
                accountTransferReqDto.setGubun("TRANSFER");

                String requestBody = om.writeValueAsString(accountTransferReqDto);
                System.out.println("????????? : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/s/account/transfer").content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findDetailAccount_test() throws Exception {
                // given
                Long number = 1111L;
                String page = "0";

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/s/account/" + number).param("page", page));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("????????? : " + responseBody);

                // then
                resultActions.andExpect(jsonPath("$.data.transactions[0].balance").value(900L));
                resultActions.andExpect(jsonPath("$.data.transactions[1].balance").value(800L));
                resultActions.andExpect(jsonPath("$.data.transactions[2].balance").value(700L));
                resultActions.andExpect(jsonPath("$.data.transactions[3].balance").value(800L));
        }

        private void dataSetting() {
                User ssar = userRepository.save(newUser("ssar", "???"));
                User cos = userRepository.save(newUser("cos", "??????,"));
                User love = userRepository.save(newUser("love", "??????"));
                User admin = userRepository.save(newUser("admin", "?????????"));

                Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
                Account cosAccount = accountRepository.save(newAccount(2222L, cos));
                Account loveAccount = accountRepository.save(newAccount(3333L, love));
                Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

                Transaction withdrawTransaction1 = transactionRepository
                                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
                Transaction depositTransaction1 = transactionRepository
                                .save(newDepositTransaction(cosAccount, accountRepository));
                Transaction transferTransaction1 = transactionRepository
                                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
                Transaction transferTransaction2 = transactionRepository
                                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
                Transaction transferTransaction3 = transactionRepository
                                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
        }
}
