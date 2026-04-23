    package com.example.gmahk.controller;

    import com.example.gmahk.dto.request.ChangePasswordRequest;
    import com.example.gmahk.dto.request.UpdatePasswordRequest;
    import com.example.gmahk.dto.response.ApiResponse;
    import com.example.gmahk.dto.response.UserResponse;
    import com.example.gmahk.service.UserService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/users")
    @RequiredArgsConstructor
    public class UserController {

        private final UserService userService;

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
            return ResponseEntity.ok(ApiResponse.ok("Data users berhasil diambil", userService.findAll()));
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Integer id) {
            return ResponseEntity.ok(ApiResponse.ok("Data user berhasil diambil", userService.findById(id)));
        }

        @GetMapping("/me")
        public ResponseEntity<ApiResponse<UserResponse>> getMe(@AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(ApiResponse.ok("Profil berhasil diambil",
                    userService.findByEmail(userDetails.getUsername())));
        }


        @PatchMapping("/me/password")
        public ResponseEntity<ApiResponse<Void>> changeMyPassword(
                @AuthenticationPrincipal UserDetails userDetails,
                @RequestBody @Valid ChangePasswordRequest request) {
            userService.changePassword(
                    userDetails.getUsername(),
                    request.getOldPassword(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok(ApiResponse.ok("Password berhasil diperbarui", null));
        }

        @PatchMapping("/{id}/password")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<Void>> updatePassword(
                @PathVariable Integer id,
                @RequestBody @Valid UpdatePasswordRequest request) {
            userService.updatePassword(id, request.getNewPassword());
            return ResponseEntity.ok(ApiResponse.ok("Password berhasil diupdate", null));
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
            userService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.ok("User berhasil dihapus", null));
        }
    }
